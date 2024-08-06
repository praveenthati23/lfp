package com.lastfarewells.backend.controller;

import com.lastfarewells.backend.dto.MessengerRequestDto;
import com.lastfarewells.backend.entity.Messenger;
import com.lastfarewells.backend.service.MessengerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/messenger")
@AllArgsConstructor
public class MessengerController {

    private final MessengerService messengerService;

    @PostMapping()
    public Messenger createMessenger(@RequestBody @Valid MessengerRequestDto messengerRequestDto) {
        return messengerService.createMessenger(messengerRequestDto);
    }

    @GetMapping("/user/{userId}")
    public Page<Messenger> findAllMessenger(@PathVariable Long userId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        return messengerService.findAllMessenger(PageRequest.of(page, size, Sort.by("id").descending()), userId);
    }

    @PutMapping("/{id}")
    public Messenger updateMessenger(@PathVariable Long id, @RequestBody @Valid MessengerRequestDto messengerRequestDto) {
        return messengerService.updateMessenger(id, messengerRequestDto);
    }

}
