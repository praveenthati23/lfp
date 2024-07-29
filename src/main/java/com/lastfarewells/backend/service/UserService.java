package com.lastfarewells.backend.service;

import com.lastfarewells.backend.dto.RegisterUserDto;
import com.lastfarewells.backend.entity.Users;

public interface UserService {

    Users registerUser(RegisterUserDto registerUserDto);

}
