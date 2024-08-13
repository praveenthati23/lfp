package com.lastfarewells.backend.service;

import com.lastfarewells.backend.dto.MessagesDto;
import com.lastfarewells.backend.entity.MessageTypeEnum;
import com.lastfarewells.backend.entity.Messages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface MessagesService {

    Messages createMessages(MessagesDto messagesDto);

    Page<Messages> findAllMessages(PageRequest pageRequest, Long userId, MessageTypeEnum messageType);

    Messages updateMessages(Long id, MessagesDto messagesDto);

    void deleteMessages(Long id);

}
