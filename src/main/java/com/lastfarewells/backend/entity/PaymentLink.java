package com.lastfarewells.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payment_link")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "user_id")
    private Long   userId;
    @Column(name = "payment_link")
    private String paymentLink;
    @Column(name = "payment_intent")
    private String paymentIntent;

    @Column(name = "created_on")
    private Instant createdOn;
    @Column(name = "updated_on")
    private Instant updatedOn;

}
