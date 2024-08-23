package com.lastfarewells.backend.controller;

import com.lastfarewells.backend.dto.MessagesDto;
import com.lastfarewells.backend.dto.MessengerRequestDto;
import com.lastfarewells.backend.entity.MessageTypeEnum;
import com.lastfarewells.backend.entity.Messages;
import com.lastfarewells.backend.entity.Messenger;
import com.lastfarewells.backend.service.MessagesService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/user/{userId}")
    public Page<Messages> findAllMessages(@PathVariable Long userId,
        @RequestParam(required = false) MessageTypeEnum messageType,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        return messagesService.findAllMessages(PageRequest.of(page, size, Sort.by("id").descending()), userId, messageType);
    }

    @PutMapping("/{id}")
    public Messages updateMessages(@PathVariable Long id, @RequestBody @Valid MessagesDto messagesDto) {
        return messagesService.updateMessages(id, messagesDto);
    }

    @DeleteMapping("/{id}")
    public void deleteMessages(@PathVariable("id") Long id) {
        messagesService.deleteMessages(id);
    }

}
