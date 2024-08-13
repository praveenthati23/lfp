package com.lastfarewells.backend.repository;

import com.lastfarewells.backend.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessagesRepository extends JpaRepository<Messages, Long> {

}
