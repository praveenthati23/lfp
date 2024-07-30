package com.lastfarewells.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.keycloak.representations.AccessTokenResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccessTokenDto {

    private Long                id;
    private AccessTokenResponse token;

}
