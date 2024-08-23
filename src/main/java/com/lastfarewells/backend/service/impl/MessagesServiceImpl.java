package com.lastfarewells.backend.service.impl;

import com.lastfarewells.backend.dto.MessagesDto;
import com.lastfarewells.backend.entity.DeliveryMethodEnum;
import com.lastfarewells.backend.entity.MessageStatusEnum;
import com.lastfarewells.backend.entity.MessageTypeEnum;
import com.lastfarewells.backend.entity.Messages;
import com.lastfarewells.backend.entity.Messenger;
import com.lastfarewells.backend.entity.Recipient;
import com.lastfarewells.backend.exception.MessengerException;
import com.lastfarewells.backend.exception.MessengesException;
import com.lastfarewells.backend.repository.MessagesRepository;
import com.lastfarewells.backend.repository.MessengerRepository;
import com.lastfarewells.backend.repository.RecipientRepository;
import com.lastfarewells.backend.service.MessagesService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    @Transactional
    public Messages createMessages(MessagesDto messagesDto) {
        Messages messages = Messages.builder()
            .userId(messagesDto.getUserId()).messageType(messagesDto.getMessageType())
            .title(messagesDto.getTitle()).description(messagesDto.getDescription())
            .content(messagesDto.getContent()).fileName(messagesDto.getFileName())
            .deliverOnDeath(messagesDto.getDeliverOnDeath()).deliveryMethod(messagesDto.getDeliveryMethod())
            .eventTitle(messagesDto.getEventTitle())
            .scheduleType(messagesDto.getScheduleType()).deliveryDate(messagesDto.getDeliveryDate()).build();
        messages.setStatus(MessageStatusEnum.COMPLETED);
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
                        .isUserRecipient(messagesDto.getRecipient().getIsUserRecipient()).build();
            messages.setRecipient(recipientRepository.save(recipient));
        }
        if (messagesDto.getMessenger() != null) {
            Messenger messenger = messengerRepository.findById(messagesDto.getMessenger())
                .orElseThrow(() -> new MessengerException("Messenger request not found"));
            messages.setMessenger(messenger);
        }
        messages.setCreatedOn(Instant.now());
        return messagesRepository.save(messages);
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
        messages.setUpdatedOn(Instant.now());
        if (messagesDto.getMessenger() != null && !messagesDto.getMessenger().equals(messages.getMessenger())) {
            Messenger messenger = messengerRepository.findById(messagesDto.getMessenger())
                .orElseThrow(() -> new MessengerException("Messenger request not found"));
            messages.setMessenger(messenger);
        }
        if (messagesDto.getRecipient() != null) {
            if (messagesDto.getRecipient().getId() == null) {
                throw new MessengesException("Invalid recipient Id");
            }
            Recipient recipient = messages.getRecipient();
            if (messages.getRecipient().getId().equals(messagesDto.getRecipient().getId())) {
                recipient.setFirstName(messagesDto.getRecipient().getFirstName());
                recipient.setLastName(messagesDto.getRecipient().getLastName());
                recipient.setEmail(messagesDto.getRecipient().getEmail());
                recipient.setIsUserRecipient(messagesDto.getRecipient().getIsUserRecipient());
                recipient.setUpdatedOn(Instant.now());
                messages.setRecipient(recipientRepository.save(recipient));
            } else {
                messages.setRecipient(recipientRepository.findById(messagesDto.getRecipient().getId())
                    .orElseThrow(() -> new MessengesException("Invalid recipient")));
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

}
