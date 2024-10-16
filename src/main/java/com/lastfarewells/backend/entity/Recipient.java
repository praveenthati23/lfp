package com.lastfarewells.backend.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipient")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long    userId;
    @Column(name = "first_name")
    private String  firstName;
    @Column(name = "last_name")
    private String  lastName;
    @Column(name = "email")
    private String  email;
    @Column(name = "created_on")
    private Instant createdOn;
    @Column(name = "updated_on")
    private Instant updatedOn;
    @Column(name = "is_user_recipient")
    private Boolean isUserRecipient;
    
    @Column(name = "relationship")
    private String  relationship;
}
