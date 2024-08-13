package com.lastfarewells.backend.repository;

import com.lastfarewells.backend.entity.MessageTypeEnum;
import com.lastfarewells.backend.entity.Messages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessagesRepository extends JpaRepository<Messages, Long> {

    Page<Messages> findAllByUserIdAndMessageType(Long userId, MessageTypeEnum messageType, Pageable pageable);

}
