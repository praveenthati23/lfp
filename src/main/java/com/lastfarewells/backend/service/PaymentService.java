package com.lastfarewells.backend.service;

import com.lastfarewells.backend.dto.PaymentLinkDto;
import com.lastfarewells.backend.entity.PaymentLink;

public interface PaymentService {

    PaymentLink createPaymentLink(PaymentLinkDto paymentLinkDto);

    PaymentLink findPaymentLink(Long userId);

    PaymentLink updatePaymentLink(PaymentLinkDto paymentLinkDto);

}
