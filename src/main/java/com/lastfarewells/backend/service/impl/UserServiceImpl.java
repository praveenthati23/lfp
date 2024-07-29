package com.lastfarewells.backend.service.impl;

import com.lastfarewells.backend.dto.SignupDto;
import com.lastfarewells.backend.entity.Users;
import com.lastfarewells.backend.exception.UserException;
import com.lastfarewells.backend.repository.UsersRepository;
import com.lastfarewells.backend.service.IAMService;
import com.lastfarewells.backend.service.UserService;
import java.time.Instant;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

}
