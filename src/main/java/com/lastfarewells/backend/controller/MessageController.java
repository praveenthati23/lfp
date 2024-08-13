package com.lastfarewells.backend.controller;

import com.lastfarewells.backend.dto.MessagesDto;
import com.lastfarewells.backend.entity.Messages;
import com.lastfarewells.backend.service.MessagesService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/message")
@AllArgsConstructor
public class MessageController {

    private final MessagesService messagesService;

    @PostMapping("")
    public Messages createMessages(@RequestBody @Valid MessagesDto messagesDto) {
        return messagesService.createMessages(messagesDto);
    }

}
