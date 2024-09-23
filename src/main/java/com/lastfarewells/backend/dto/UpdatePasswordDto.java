package com.lastfarewells.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordDto {
    @NotNull(message = "newpassword cannot be null")
    @NotBlank(message = "newpassword is mandatory")
    private String newPassword;

    @NotNull(message = "currentPassword cannot be null")
    @NotBlank(message = "currentPassword is mandatory")
    private String currentPassword;

}
