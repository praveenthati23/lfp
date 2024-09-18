package com.lastfarewells.backend.controller;

import com.lastfarewells.backend.dto.MessagesDto;
import com.lastfarewells.backend.dto.PaymentLinkDto;
import com.lastfarewells.backend.entity.Messages;
import com.lastfarewells.backend.entity.PaymentLink;
import com.lastfarewells.backend.service.PaymentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/link")
    public PaymentLink createPaymentLink(@RequestBody @Valid PaymentLinkDto paymentLinkDto) {
        return paymentService.createPaymentLink(paymentLinkDto);
    }

    @GetMapping("/link/user/{userId}")
    public PaymentLink findPaymentLink(@PathVariable Long userId) {
        return paymentService.findPaymentLink(userId);
    }

    @PutMapping("/link")
    public PaymentLink updatePaymentLink(@RequestBody @Valid PaymentLinkDto paymentLinkDto) {
        return paymentService.updatePaymentLink(paymentLinkDto);
    }
}
