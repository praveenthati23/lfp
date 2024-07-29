package com.lastfarewells.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDto {

    @NotNull(message = "firstName cannot be null")
    @NotBlank(message = "firstName is mandatory")
    private String firstName;
    private String lastName;
    @NotNull(message = "Birth date is required in MM/dd/yyyy")
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date   birthDate;

    @NotNull(message = "iamId cannot be null")
    @NotBlank(message = "iamId is mandatory")
    private String iamId;

}
