package com.lastfarewells.backend.service;

import com.lastfarewells.backend.dto.LoginDto;
import com.lastfarewells.backend.dto.PasswordResetDto;
import com.lastfarewells.backend.dto.SignupDto;
import com.lastfarewells.backend.dto.UserAccessTokenDto;
import com.lastfarewells.backend.dto.VerifyEmailDto;
import com.lastfarewells.backend.entity.Users;

public interface UserService {

    //Users registerUser(RegisterUserDto registerUserDto);
    void registerUser(SignupDto signupDto);

    UserAccessTokenDto authenticateUser(LoginDto loginDto);

    void forgotPassword(String email);

    void resetUserPassword(PasswordResetDto passwordResetDto);

    void verifyEmail(VerifyEmailDto verifyEmailDto);

    Users getUser();
}
