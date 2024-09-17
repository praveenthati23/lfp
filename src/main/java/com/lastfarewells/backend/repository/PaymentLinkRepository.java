package com.lastfarewells.backend.repository;

import com.lastfarewells.backend.entity.PaymentLink;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentLinkRepository extends JpaRepository<PaymentLink, Long> {

    Optional<PaymentLink> findByUserId(Long userId);

}
