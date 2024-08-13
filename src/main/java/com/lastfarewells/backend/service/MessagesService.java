package com.lastfarewells.backend.service;

import com.lastfarewells.backend.dto.MessagesDto;
import com.lastfarewells.backend.entity.Messages;

public interface MessagesService {

    Messages createMessages(MessagesDto messagesDto);

}
