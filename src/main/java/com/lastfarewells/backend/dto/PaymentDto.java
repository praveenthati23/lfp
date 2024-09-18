package com.lastfarewells.backend.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    private Long       userId;
    private String     transactionId;
    private BigDecimal amount;
    private String     checkoutId;
    private String     paymentIntent;

}
