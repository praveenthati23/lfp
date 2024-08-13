package com.lastfarewells.backend.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lastfarewells.backend.entity.Recipient;

public interface RecipientRepository extends JpaRepository<Recipient, Long> {

	Optional<Recipient> findByEmail(String email);

	Page<Recipient> findAllByUserIdAndIsUserRecipient(Long userId, boolean isUserRecipient, Pageable pageable);

	Optional<Recipient> findByEmailAndUserId(String email, Long userId);

}
