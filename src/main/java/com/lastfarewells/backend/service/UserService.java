package com.lastfarewells.backend.service;

import com.lastfarewells.backend.dto.SignupDto;

public interface UserService {

    //Users registerUser(RegisterUserDto registerUserDto);
    void registerUser(SignupDto signupDto);

}
