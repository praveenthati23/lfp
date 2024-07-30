package com.lastfarewells.backend.service.impl;

import com.lastfarewells.backend.dto.LoginDto;
import com.lastfarewells.backend.dto.PasswordResetDto;
import com.lastfarewells.backend.dto.SignupDto;
import com.lastfarewells.backend.dto.UserAccessTokenDto;
import com.lastfarewells.backend.entity.Users;
import com.lastfarewells.backend.exception.UserAuthenticationException;
import com.lastfarewells.backend.exception.UserException;
import com.lastfarewells.backend.repository.UsersRepository;
import com.lastfarewells.backend.service.IAMService;
import com.lastfarewells.backend.service.UserService;
import com.lastfarewells.backend.utils.JWTUtils;
import java.time.Instant;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;
    private final IAMService      keycloakService;

   /* @Override
    public Users registerUser(RegisterUserDto registerUserDto) {
        log.info("Registering User with IAM id : {}", registerUserDto.getIamId());
        Users users = Users.builder().firstName(registerUserDto.getFirstName())
            .lastName(registerUserDto.getLastName()).iamId(registerUserDto.getIamId()).birthDate(registerUserDto.getBirthDate())
            .createdOn(Instant.now()).build();
        return usersRepository.save(users);
    }*/


    @Override
    @Transactional
    public void registerUser(SignupDto signupDto) {
        Optional<Users> existingUserAuth = usersRepository.findByEmail(signupDto.getEmail());
        if (existingUserAuth.isPresent()) {
            throw new UserException("User with email already exists");
        }

        // Save user details
        try {
            log.info("Registering User with email: {}", signupDto.getEmail());
            // Create user in IAM
            String iamId = keycloakService.createUser(signupDto);

            Users users = Users.builder().firstName(signupDto.getFirstName())
                .lastName(signupDto.getLastName()).iamId(iamId).birthDate(signupDto.getBirthDate())
                .createdOn(Instant.now()).email(signupDto.getEmail()).roleId(1).emailVerified(false)
                .isTrustor(false).build();
            usersRepository.save(users);

        } catch (Exception e) {
            // Compensation action: delete the user from auth service if profile registration fails
            //userAuthRepository.deleteByEmail(signupDto.getEmail());
            log.error("User registration failed : {}", e.getMessage());
            throw new UserException("User registration failed");
        }

    }

    @Override
    public UserAccessTokenDto authenticateUser(LoginDto loginDto) {
        Users user = usersRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new UserException("User with email not found"));

        AccessTokenResponse accessTokenResponse = keycloakService.login(loginDto);

        Boolean isEmailVerified = (Boolean) JWTUtils.decodeJWT(accessTokenResponse.getToken())
            .getPayload().toJSONObject().get("email_verified");
        if (isEmailVerified == null || !isEmailVerified) {
            throw new UserAuthenticationException("User's email is not verified!!");
        }
        if (!user.getEmailVerified() && isEmailVerified) {
            user.setEmailVerified(true);
        }
        log.info("User successfully verified for login {}", loginDto.getEmail());
        // Update user's last login
        user.setLastLogin(Instant.now());
        usersRepository.save(user);
        return new UserAccessTokenDto(user.getId(), accessTokenResponse);
    }

    @Override
    public void forgotPassword(String email) {
        Users user = usersRepository.findByEmail(email).orElseThrow(() -> new UserException("User with email not found"));
        log.info("User {} requested for password reset", email);

        String token = JWTUtils.generateVerificationToken(user.getIamId());
        //TODO remove println as soon as mail sender is done
        System.out.println("**** : " + token);
        // Store token for validation
        // Send email
        //mailService.sendResetPassword(user.getIamId());
        //https://lastfarewells.vercel.app/reset-password/token
    }

    @Override
    public void resetUserPassword(PasswordResetDto passwordResetDto) {
        // Find PasswordResetToken by token
        Users user = usersRepository.findByEmail(passwordResetDto.getEmail())
            .orElseThrow(() -> new UserException("User with email not found"));
        // Validate token expiration
        String subject = JWTUtils.getEmailFromToken(passwordResetDto.getToken());
        Users subjectUser = usersRepository.findByIamId(subject)
            .orElseThrow(() -> new UserException("Invalid token for User"));
        if (!subjectUser.getEmail().equals(user.getEmail())) {
            throw new UserAuthenticationException("Invalid email for password reset request for provided token");
        }
        if (!JWTUtils.verifyToken(passwordResetDto.getToken())) {
            throw new UserAuthenticationException("Invalid or expired token");
        }
        try {
            keycloakService.updatePassword(user.getIamId(), passwordResetDto.getPassword());
        } catch (Exception e) {
            log.error("Password Reset failed : {}", e.getMessage());
            throw new UserException("Password Reset failed");
        }
    }

}
