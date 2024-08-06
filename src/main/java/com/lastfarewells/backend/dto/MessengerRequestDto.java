package com.lastfarewells.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessengerRequestDto {

    @NotNull(message = "firstName cannot be null")
    @NotBlank(message = "firstName is mandatory")
    private String firstName;
    private String lastName;
    @NotNull(message = "email cannot be null")
    @NotBlank(message = "email is mandatory")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
        flags = Pattern.Flag.CASE_INSENSITIVE, message = "Invalid email")
    private String email;

    @NotNull(message = "requestorId cannot be null")
    private Long requestorId;

}
