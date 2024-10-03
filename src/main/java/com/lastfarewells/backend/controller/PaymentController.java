package com.lastfarewells.backend.controller;

import com.lastfarewells.backend.dto.PaymentDto;
import com.lastfarewells.backend.dto.PaymentLinkDto;
import com.lastfarewells.backend.entity.Payment;
import com.lastfarewells.backend.entity.PaymentLink;
import com.lastfarewells.backend.service.PaymentService;
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

    @DeleteMapping("/link/user/{userId}")
    public void deletePaymentLink(@PathVariable("userId") Long userId) {
        paymentService.deletePaymentLink(userId);
    }

    @PostMapping("")
    public Payment createPayment(@RequestBody @Valid PaymentDto paymentDto) {
        return paymentService.createPayment(paymentDto);
    }

    @GetMapping("/user/{userId}")
    public Page<Payment> findAllPayments(@PathVariable Long userId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        return paymentService.findAllPayments(PageRequest.of(page, size, Sort.by("id").descending()), userId);
    }

    @GetMapping("")
    public Payment findPaymentByCheckoutId(@RequestParam("checkoutId") String checkoutId) {
        return paymentService.findPaymentByCheckoutId(checkoutId);
    }

}
