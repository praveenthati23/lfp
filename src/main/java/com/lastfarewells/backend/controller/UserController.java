package com.lastfarewells.backend.controller;

import com.lastfarewells.backend.dto.RegisterUserDto;
import com.lastfarewells.backend.dto.SignupDto;
import com.lastfarewells.backend.entity.Users;
import com.lastfarewells.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

   /* @PostMapping("/register")
    public Users registerUser(@RequestBody @Valid RegisterUserDto registerUserDto) {
        return userService.registerUser(registerUserDto);
    }
*/
    @PostMapping("/signup")
    public void signUp(@RequestBody @Valid SignupDto signupDto) {
        userService.registerUser(signupDto);
    }
}
