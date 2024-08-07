package com.lastfarewells.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessengerVerificationDto {

    @NotNull(message = "password cannot be null")
    @NotBlank(message = "password is mandatory")
    private String invitationToken;

}
