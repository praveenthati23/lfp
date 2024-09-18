package com.lastfarewells.backend.service.impl;

import com.lastfarewells.backend.dto.PaymentLinkDto;
import com.lastfarewells.backend.entity.PaymentLink;
import com.lastfarewells.backend.exception.PaymentException;
import com.lastfarewells.backend.repository.PaymentLinkRepository;
import com.lastfarewells.backend.service.PaymentService;
import java.time.Instant;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentLinkRepository paymentLinkRepository;

    @Override
    public PaymentLink createPaymentLink(PaymentLinkDto paymentLinkDto) {
        if (paymentLinkDto.getUserId() == null) {
            throw new PaymentException("UserId is mandatory");
        }
        Optional<PaymentLink> existingPaymentLink = paymentLinkRepository.findByUserId(paymentLinkDto.getUserId());
        if (existingPaymentLink.isPresent()) {
            existingPaymentLink.get().setPaymentLink(paymentLinkDto.getPaymentLink());
            existingPaymentLink.get().setPaymentIntent(paymentLinkDto.getPaymentIntent());
            existingPaymentLink.get().setUpdatedOn(Instant.now());
            return paymentLinkRepository.save(existingPaymentLink.get());
        } else {
            PaymentLink paymentLink = PaymentLink.builder()
                .userId(paymentLinkDto.getUserId()).paymentLink(paymentLinkDto.getPaymentLink())
                .paymentIntent(paymentLinkDto.getPaymentIntent()).build();
            paymentLink.setCreatedOn(Instant.now());
            log.info("Saving new paymentLink for userId {}", paymentLink.getUserId());
            return paymentLinkRepository.save(paymentLink);
        }
    }

    @Override
    public PaymentLink findPaymentLink(Long userId) {
        return paymentLinkRepository.findByUserId(userId)
            .orElseThrow(() -> new PaymentException("Payment Link not found"));
    }

    @Override
    public PaymentLink updatePaymentLink(PaymentLinkDto paymentLinkDto) {
        if (paymentLinkDto.getUserId() == null) {
            throw new PaymentException("UserId is mandatory");
        }
        PaymentLink paymentLink = paymentLinkRepository.findByUserId(paymentLinkDto.getUserId())
            .orElseThrow(() -> new PaymentException("Payment Link not found"));
        if (StringUtils.isNotEmpty(paymentLinkDto.getPaymentLink())) {
            paymentLink.setPaymentLink(paymentLinkDto.getPaymentLink());
        }
        if (StringUtils.isNotEmpty(paymentLinkDto.getPaymentIntent())) {
            paymentLink.setPaymentLink(paymentLinkDto.getPaymentIntent());
        }
        return paymentLinkRepository.save(paymentLink);
    }


}
