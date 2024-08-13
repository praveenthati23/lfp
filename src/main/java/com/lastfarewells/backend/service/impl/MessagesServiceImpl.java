package com.lastfarewells.backend.service.impl;

import com.lastfarewells.backend.dto.MessagesDto;
import com.lastfarewells.backend.entity.MessageStatusEnum;
import com.lastfarewells.backend.entity.Messages;
import com.lastfarewells.backend.entity.Recipient;
import com.lastfarewells.backend.repository.MessagesRepository;
import com.lastfarewells.backend.repository.RecipientRepository;
import com.lastfarewells.backend.service.MessagesService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessagesServiceImpl implements MessagesService {

    private final RecipientRepository recipientRepository;
    private final MessagesRepository  messagesRepository;

    @Override
    @Transactional
    public Messages createMessages(MessagesDto messagesDto) {
        Messages messages = Messages.builder()
            .userId(messagesDto.getUserId()).messageType(messagesDto.getMessageType())
            .title(messagesDto.getTitle()).description(messagesDto.getDescription())
            .content(messagesDto.getContent()).fileName(messagesDto.getFileName())
            .deliverOnDeath(messagesDto.getDeliverOnDeath()).deliveryMethod(messagesDto.getDeliveryMethod())
            .scheduleType(messagesDto.getScheduleType()).deliveryDate(messagesDto.getDeliveryDate()).build();
        messages.setStatus(MessageStatusEnum.COMPLETED);

        if (messagesDto.getRecipient() != null) {
            Recipient recipient = Recipient.builder()
                .userId(messagesDto.getUserId()).firstName(messagesDto.getRecipient().getFirstName())
                .lastName(messagesDto.getRecipient().getLastName())
                .email(messagesDto.getRecipient().getEmail()).createdOn(Instant.now())
                .isUserRecipient(messagesDto.getRecipient().getIsUserRecipient()).build();
            messages.setRecipient(recipientRepository.save(recipient));
        }
        messages.setCreatedOn(Instant.now());
        return messagesRepository.save(messages);
    }

}
