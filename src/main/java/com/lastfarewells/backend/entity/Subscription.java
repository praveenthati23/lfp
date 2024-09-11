package com.lastfarewells.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subscription")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscription {

    @Id
    @Column(name = "id")
    private Long id;


    @Column(name = "name")
    private String name;
    @Column(name = "payment_id")
    private Long   paymentId;
    //@Column(name = "plan_id")
    //private Long   plan_id;


    @Column(name = "created_on")
    private Instant createdOn;
    @Column(name = "updated_on")
    private Instant updatedOn;

    @OneToOne(fetch = FetchType.LAZY) // Lazy fetch for retrieval
    @JoinColumn(name = "plan_id")     // Foreign key to the Plan entity
    private Plan plan;
}
