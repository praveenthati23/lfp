package com.lastfarewells.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupDto {

    @NotNull(message = "email cannot be null")
    @NotBlank(message = "email is mandatory")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
        flags = Pattern.Flag.CASE_INSENSITIVE, message = "Invalid email")
    private String email;

    @NotNull(message = "password cannot be null")
    @NotBlank(message = "password is mandatory")
    private String password;
    @NotNull(message = "firstName cannot be null")
    @NotBlank(message = "firstName is mandatory")
    private String firstName;
    private String lastName;
    @NotNull(message = "Birth date is required in MM/dd/yyyy")
    //@DateTimeFormat(pattern = "MM/dd/yyyy")
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date   birthDate;

}
