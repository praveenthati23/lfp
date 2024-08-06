package com.lastfarewells.backend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
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
@Table(name = "messengers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Messenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "messenger_for")
    private Long    messengerFor;
    @Column(name = "first_name")
    private String  firstName;
    @Column(name = "last_name")
    private String  lastName;
    @Column(name = "email")
    private String  email;
    @Column(name = "is_confirmed")
    private Boolean isConfirmed;
    @Column(name = "invitation_token")
    @JsonProperty(access = Access.WRITE_ONLY)
    private String  invitationToken;
    @Column(name = "messenger_user_id")
    private Long    messengerUserId;
    @Column(name = "custom_message")
    private String  customMessage;
    @Column(name = "created_on")
    private Instant createdOn;
    @Column(name = "updated_on")
    private Instant updatedOn;

}
