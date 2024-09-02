package com.lastfarewells.backend.repository;

import com.lastfarewells.backend.entity.Memorial;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemorialRepository extends JpaRepository<Memorial, Long> {

    Optional<Memorial> findByUserId(Long userId);

}
