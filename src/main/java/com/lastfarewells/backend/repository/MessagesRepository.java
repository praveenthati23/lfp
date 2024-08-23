package com.lastfarewells.backend.repository;

import com.lastfarewells.backend.entity.MessageStatusEnum;
import com.lastfarewells.backend.entity.MessageTypeEnum;
import com.lastfarewells.backend.entity.Messages;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessagesRepository extends JpaRepository<Messages, Long> {

    Page<Messages> findAllByUserIdAndMessageType(Long userId, MessageTypeEnum messageType, Pageable pageable);

    List<Messages> findAllByDeliveryDateAndStatusNotIn(Date deliveryDate, List<MessageStatusEnum> status);
    Page<Messages> findAllByUserId(Long userId, Pageable pageable);

}
