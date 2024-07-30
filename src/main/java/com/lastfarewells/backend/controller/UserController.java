package com.lastfarewells.backend.controller;

import com.lastfarewells.backend.dto.LoginDto;
import com.lastfarewells.backend.dto.PasswordResetDto;
import com.lastfarewells.backend.dto.SignupDto;
import com.lastfarewells.backend.dto.UserAccessTokenDto;
import com.lastfarewells.backend.dto.VerifyEmailDto;
import com.lastfarewells.backend.entity.Users;
import com.lastfarewells.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    /* @PostMapping("/register")
     public Users registerUser(@RequestBody @Valid RegisterUserDto registerUserDto) {
         return userService.registerUser(registerUserDto);
     }
 */
    @PostMapping("/auth/signup")
    public void signUp(@RequestBody @Valid SignupDto signupDto) {
        userService.registerUser(signupDto);
    }


    @PostMapping("/auth/login")
    public UserAccessTokenDto login(@RequestBody @Valid LoginDto loginDto) {
        return userService.authenticateUser(loginDto);
    }

    @GetMapping("/auth/forgot-password")
    public String forgotPassword(@RequestParam("email") String email) {
        userService.forgotPassword(email);
        return "Successfully sent reset-password link!!";
    }

    @PutMapping("/auth/reset-password")
    public String resetUserPassword(@RequestBody @Valid PasswordResetDto passwordResetDto) {
        userService.resetUserPassword(passwordResetDto);
        return "Password successfully reset!";
    }

    @PutMapping("/auth/verify-email")
    public String verifyEmail(@RequestBody @Valid VerifyEmailDto verifyEmailDto) {
        userService.verifyEmail(verifyEmailDto);
        return "Thank you for signing up with Last Farewells, your account has been verified.";
    }

    @GetMapping("/user/me")
    public Users getUser() {
        return userService.getUser();
    }

}
