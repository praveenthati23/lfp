package com.lastfarewells.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessengeRecipientDto {

    private Long    id;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean isUserRecipient;
}
