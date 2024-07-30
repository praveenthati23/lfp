package com.lastfarewells.backend.service;

import com.lastfarewells.backend.dto.LoginDto;
import com.lastfarewells.backend.dto.SignupDto;
import org.keycloak.representations.AccessTokenResponse;

public interface IAMService {

    String createUser(SignupDto signupDto);

    AccessTokenResponse login(LoginDto loginDTO);

    void sendResetPassword(String userId);

    void logout(String token);

    void updatePassword(String userId, String password);
}
