package com.lastfarewells.backend.controller;

import com.lastfarewells.backend.dto.MessagesDto;
import com.lastfarewells.backend.dto.PaymentLinkDto;
import com.lastfarewells.backend.entity.Messages;
import com.lastfarewells.backend.entity.PaymentLink;
import com.lastfarewells.backend.service.PaymentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
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


}
