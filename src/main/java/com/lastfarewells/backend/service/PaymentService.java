package com.lastfarewells.backend.service;

import com.lastfarewells.backend.dto.PaymentDto;
import com.lastfarewells.backend.dto.PaymentLinkDto;
import com.lastfarewells.backend.entity.Payment;
import com.lastfarewells.backend.entity.PaymentLink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface PaymentService {

    PaymentLink createPaymentLink(PaymentLinkDto paymentLinkDto);

    PaymentLink findPaymentLink(Long userId);

    PaymentLink updatePaymentLink(PaymentLinkDto paymentLinkDto);

    void deletePaymentLink(Long userId);

    Payment createPayment(PaymentDto paymentDto);

    Page<Payment> findAllPayments(PageRequest pageRequest, Long userId);

}
