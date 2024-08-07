package com.lastfarewells.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessengerVerificationResponseDto {

    private String              firstName;
    private String              lastName;
    private String              email;
    private MessengerForDetails messengerFor;
    private Boolean             isNewUser;

    @Data
    @Builder
    public static class MessengerForDetails {

        private Long   userId;
        private String firstName;
        private String lastName;

    }

}

