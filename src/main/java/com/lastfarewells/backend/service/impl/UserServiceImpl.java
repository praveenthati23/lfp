package com.lastfarewells.backend.service.impl;

import com.lastfarewells.backend.dto.RegisterUserDto;
import com.lastfarewells.backend.entity.Users;
import com.lastfarewells.backend.repository.UsersRepository;
import com.lastfarewells.backend.service.UserService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;

    @Override
    public Users registerUser(RegisterUserDto registerUserDto) {
        log.info("Registering User with IAM id : {}", registerUserDto.getIamId());
        Users users = Users.builder().firstName(registerUserDto.getFirstName())
            .lastName(registerUserDto.getLastName()).iamId(registerUserDto.getIamId()).birthDate(registerUserDto.getBirthDate())
            .createdOn(Instant.now()).build();
        return usersRepository.save(users);
    }

}
