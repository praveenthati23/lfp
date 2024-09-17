package com.lastfarewells.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentLinkDto {

    private Long   userId;
    private String paymentLink;
    private String paymentIntent;

}
