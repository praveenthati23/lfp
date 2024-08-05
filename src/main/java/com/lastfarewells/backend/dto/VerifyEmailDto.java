package com.lastfarewells.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyEmailDto {

    @NotNull(message = "token cannot be null")
    @NotBlank(message = "token is mandatory")
    private String token;

}

