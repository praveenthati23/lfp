package com.lastfarewells.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessengerResendDto {

    private Long id;

    @NotNull(message = "initiator cannot be null")
    @NotBlank(message = "initiator is mandatory")
    private String initiator;

}
