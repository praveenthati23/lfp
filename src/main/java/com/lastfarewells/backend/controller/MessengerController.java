package com.lastfarewells.backend.controller;

import com.lastfarewells.backend.dto.MessengerActionDto;
import com.lastfarewells.backend.dto.MessengerForDto;
import com.lastfarewells.backend.dto.MessengerRequestDto;
import com.lastfarewells.backend.dto.MessengerResendDto;
import com.lastfarewells.backend.dto.MessengerVerificationDto;
import com.lastfarewells.backend.dto.MessengerVerificationResponseDto;
import com.lastfarewells.backend.entity.Messenger;
import com.lastfarewells.backend.service.MessengerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/verify")
    public MessengerVerificationResponseDto verifyMessengerToken(@RequestBody @Valid MessengerVerificationDto messengerRequestDto) {
        return messengerService.verifyMessengerToken(messengerRequestDto);
    }

    @PostMapping("/resend-invitation")
    public ResponseEntity<?> resendMessengerInvitation(@RequestBody @Valid MessengerResendDto messengerResendDto) {
        messengerService.resendMessengerInvitation(messengerResendDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accept")
    public ResponseEntity<?> acceptInvitation(@RequestBody @Valid MessengerActionDto messengerActionDto) {
        messengerService.acceptInvitation(messengerActionDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/for/user/{userId}")
    public Page<MessengerForDto> findAllUsersForMessengerfor(@PathVariable Long userId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        return messengerService.findAllUsersForMessengerfor(PageRequest.of(page, size, Sort.by("id").descending()), userId);
    }
}
