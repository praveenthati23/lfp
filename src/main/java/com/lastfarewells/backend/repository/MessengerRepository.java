package com.lastfarewells.backend.repository;

import com.lastfarewells.backend.entity.Messenger;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessengerRepository extends JpaRepository<Messenger, Long> {

    Optional<Messenger> findByMessengerForAndEmail(Long messengerFor, String email);

    Page<Messenger> findAllByMessengerFor(Long messengerFor, Pageable pageable);

    Optional<Messenger> findByInvitationToken(String invitationToken);

}
