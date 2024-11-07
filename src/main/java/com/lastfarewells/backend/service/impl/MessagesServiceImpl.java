package com.lastfarewells.backend.service.impl;

import com.lastfarewells.backend.dto.MessagesDto;
import com.lastfarewells.backend.dto.MessagesResponseDto;
import com.lastfarewells.backend.entity.DeliveryMethodEnum;
import com.lastfarewells.backend.entity.MessageStatusEnum;
import com.lastfarewells.backend.entity.MessageTypeEnum;
import com.lastfarewells.backend.entity.Messages;
import com.lastfarewells.backend.entity.Messenger;
import com.lastfarewells.backend.entity.Recipient;
import com.lastfarewells.backend.entity.Users;
import com.lastfarewells.backend.exception.MessengerException;
import com.lastfarewells.backend.exception.MessengesException;
import com.lastfarewells.backend.repository.MessagesRepository;
import com.lastfarewells.backend.repository.MessengerRepository;
import com.lastfarewells.backend.repository.RecipientRepository;
import com.lastfarewells.backend.repository.UsersRepository;
import com.lastfarewells.backend.service.MessagesService;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessagesServiceImpl implements MessagesService {

    private final RecipientRepository recipientRepository;
    private final MessagesRepository  messagesRepository;
    private final MessengerRepository messengerRepository;
    private final UsersRepository usersRepository;

    @Override
    @Transactional
    public MessagesResponseDto createMessages(MessagesDto messagesDto) {
        Messages messages = Messages.builder()
            .userId(messagesDto.getUserId()).messageType(messagesDto.getMessageType())
            .title(messagesDto.getTitle()).description(messagesDto.getDescription())
            .content(messagesDto.getContent()).fileName(messagesDto.getFileName())
            .deliverOnDeath(messagesDto.getDeliverOnDeath()).deliveryMethod(messagesDto.getDeliveryMethod())
            .eventTitle(messagesDto.getEventTitle())
            .scheduleType(messagesDto.getScheduleType()).deliveryDate(messagesDto.getDeliveryDate())
            .deliverYrsAfterDeath(messagesDto.getDeliverYrsAfterDeath()).build();
        if (messagesDto.getStatus() != null) {
            messages.setStatus(messagesDto.getStatus());
        } else {
            messages.setStatus(MessageStatusEnum.COMPLETED);
        }

        if (messages.getDeliveryMethod() == null) {
            messages.setDeliveryMethod(DeliveryMethodEnum.EMAIL);
        }

        if (messagesDto.getRecipient() != null && (messagesDto.getRecipient().getId() != null || messagesDto.getRecipient().getEmail() != null)) {
            Recipient recipient = messagesDto.getRecipient().getId() != null ?
                recipientRepository.findById(messagesDto.getRecipient().getId()).orElseThrow(() -> new MessengesException("Invalid recipient"))
                :
                    Recipient.builder()
                        .userId(messagesDto.getUserId()).firstName(messagesDto.getRecipient().getFirstName())
                        .lastName(messagesDto.getRecipient().getLastName())
                        .email(messagesDto.getRecipient().getEmail()).createdOn(Instant.now())
                        .isUserRecipient(messagesDto.getRecipient().getIsUserRecipient()).relationship(messagesDto.getRecipient().getRelationship()).build();
            messages.setRecipient(recipientRepository.save(recipient));
        } else if (messagesDto.getRecipient() != null && messagesDto.getStatus().equals(MessageStatusEnum.DRAFT)) {
            messages.setRecipient(recipientRepository.save(Recipient.builder()
                .userId(messagesDto.getUserId()).firstName(messagesDto.getRecipient().getFirstName())
                .lastName(messagesDto.getRecipient().getLastName())
                .email(messagesDto.getRecipient().getEmail()).createdOn(Instant.now())
                .isUserRecipient(messagesDto.getRecipient().getIsUserRecipient()).build()));
        }
        if (messagesDto.getMessenger() != null) {
            Messenger messenger = messengerRepository.findById(messagesDto.getMessenger())
                .orElseThrow(() -> new MessengerException("Messenger request not found"));
            messages.setMessenger(messenger);
        }
        messages.setCreatedOn(Instant.now());
        messages.setUpdatedOn(Instant.now());
        messagesRepository.save(messages);

        return checkAndBuildMessage(messages);
    }

    @Override
    public Page<Messages> findAllMessages(PageRequest pageRequest, Long userId, MessageTypeEnum messageType) {
        if (messageType != null) {
            return messagesRepository.findAllByUserIdAndMessageType(userId, messageType, pageRequest);
        }
        return messagesRepository.findAllByUserId(userId, pageRequest);
    }

    @Override
    public Messages updateMessages(Long id, MessagesDto messagesDto) {
        Messages messages = messagesRepository.findById(id)
            .orElseThrow(() -> new MessengesException("Message request not found"));
        log.info("Updating Message {} for user {}", id, messages.getUserId());

		if (messagesDto.getStatus() != null) {
			messages.setStatus(messagesDto.getStatus());
		}
		messages.setTitle(messagesDto.getTitle());
		messages.setDescription(messagesDto.getDescription());
		messages.setFileName(messagesDto.getFileName());
		messages.setContent(messagesDto.getContent());
		messages.setDeliverOnDeath(messagesDto.getDeliverOnDeath());
		messages.setDeliveryMethod(messagesDto.getDeliveryMethod());
		if (messages.getDeliveryMethod() == null) {
			messages.setDeliveryMethod(DeliveryMethodEnum.EMAIL);
		}
		messages.setScheduleType(messagesDto.getScheduleType());
		messages.setDeliveryDate(messagesDto.getDeliveryDate());
		messages.setEventTitle(messagesDto.getEventTitle());
		messages.setDeliverYrsAfterDeath(messagesDto.getDeliverYrsAfterDeath());
		messages.setUpdatedOn(Instant.now());
		if (messagesDto.getMessenger() != null && !messagesDto.getMessenger().equals(messages.getMessenger())) {
			Messenger messenger = messengerRepository.findById(messagesDto.getMessenger())
					.orElseThrow(() -> new MessengerException("Messenger request not found"));
			messages.setMessenger(messenger);
		} else {
			messages.setMessenger(null);
		}
		if (messagesDto.getRecipient() != null) {
			if (messagesDto.getRecipient().getId() == null) {
				// save a new recipient
				messages.setRecipient(recipientRepository.save(Recipient.builder().userId(messagesDto.getUserId())
						.firstName(messagesDto.getRecipient().getFirstName())
						.lastName(messagesDto.getRecipient().getLastName()).email(messagesDto.getRecipient().getEmail())
						.createdOn(Instant.now()).isUserRecipient(messagesDto.getRecipient().getIsUserRecipient())
						.relationship(messagesDto.getRecipient().getRelationship()).build()));
			} else {
				Recipient recipient = messages.getRecipient();
				if (messages.getRecipient().getId().equals(messagesDto.getRecipient().getId())) {
					if (StringUtils.isNotEmpty(messagesDto.getRecipient().getFirstName())) {
						recipient.setFirstName(messagesDto.getRecipient().getFirstName());
					}
					if (StringUtils.isNotEmpty(messagesDto.getRecipient().getLastName())) {
						recipient.setLastName(messagesDto.getRecipient().getLastName());
					}
					if (StringUtils.isNotEmpty(messagesDto.getRecipient().getEmail())) {
						recipient.setEmail(messagesDto.getRecipient().getEmail());
					}
					if (messagesDto.getRecipient().getIsUserRecipient() != null) {
						recipient.setIsUserRecipient(messagesDto.getRecipient().getIsUserRecipient());
					}
					if (StringUtils.isNotEmpty(messagesDto.getRecipient().getRelationship())) {
						recipient.setRelationship(messagesDto.getRecipient().getRelationship());
					}

					recipient.setUpdatedOn(Instant.now());
					messages.setRecipient(recipientRepository.save(recipient));
				} else {
					messages.setRecipient(recipientRepository.findById(messagesDto.getRecipient().getId())
							.orElseThrow(() -> new MessengesException("Invalid recipient")));
				}
			}

        }
        return messagesRepository.save(messages);
    }

    @Override
    public void deleteMessages(Long id) {
        Messages messages = messagesRepository.findById(id)
            .orElseThrow(() -> new MessengesException("Message request not found"));
        log.info("Deleting message with id : {}", id);
        messagesRepository.deleteById(messages.getId());
    }

    @Override
    public void updateMessageStatusForEmail(String email) {
        List<Messages> todaysMessages = messagesRepository.findAllByDeliveryDate(new Date());
        todaysMessages.forEach(message -> {
            if (message.getRecipient() != null && StringUtils.isNotEmpty(message.getRecipient().getEmail()) &&
                message.getRecipient().getEmail().equalsIgnoreCase(email)) {
                log.info("Updating message status for {} to FAILED", message.getId());
                message.setStatus(MessageStatusEnum.FAILED);
                messagesRepository.save(message);
                return;
            }
            if (message.getMessenger() != null && StringUtils.isNotEmpty(message.getMessenger().getEmail()) &&
                message.getMessenger().getEmail().equalsIgnoreCase(email)) {
                log.info("Updating message status for {} to FAILED", message.getId());
                message.setStatus(MessageStatusEnum.FAILED);
                messagesRepository.save(message);
            }
        });
    }


    private MessagesResponseDto checkAndBuildMessage(Messages messages) {
        MessagesResponseDto messagesResponseDto = MessagesResponseDto.builder().id(messages.getId()).userId(messages.getUserId())
            .recipient(messages.getRecipient()).messenger(messages.getMessenger()).status(messages.getStatus())
            .messageType(messages.getMessageType()).title(messages.getTitle()).description(messages.getDescription())
            .content(messages.getContent()).fileName(messages.getFileName()).deliverOnDeath(messages.getDeliverOnDeath())
            .deliveryMethod(messages.getDeliveryMethod()).scheduleType(messages.getScheduleType())
            .deliveryDate(messages.getDeliveryDate()).eventTitle(messages.getEventTitle()).createdOn(messages.getCreatedOn())
            .deliverYrsAfterDeath(messages.getDeliverYrsAfterDeath()).build();

        // Check is first message
        Optional<Users> user = usersRepository.findById(messages.getUserId());
        if (user.isPresent()) {
            switch (messages.getMessageType()) {
                case LETTER -> {
                    messagesResponseDto.setIsFirstMessage(!user.get().getIsFirstLetterCreated());
                    if (!user.get().getIsFirstLetterCreated()) {
                        user.get().setIsFirstLetterCreated(true);
                        usersRepository.save(user.get());
                    }
                }
                case VIDEO -> {
                    messagesResponseDto.setIsFirstMessage(!user.get().getIsFirstVideoCreated());
                    if (!user.get().getIsFirstVideoCreated()) {
                        user.get().setIsFirstVideoCreated(true);
                        usersRepository.save(user.get());
                    }
                }
                case AUDIO -> {
                    messagesResponseDto.setIsFirstMessage(!user.get().getIsFirstAudioCreated());
                    if (!user.get().getIsFirstAudioCreated()) {
                        user.get().setIsFirstAudioCreated(true);
                        usersRepository.save(user.get());
                    }
                }
            }
        }
        return messagesResponseDto;
    }
}
