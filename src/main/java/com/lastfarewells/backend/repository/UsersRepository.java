package com.lastfarewells.backend.repository;

import com.lastfarewells.backend.entity.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Users findByIamId(String iamId);

    Optional<Users> findByEmail(String email);

}
