package com.lastfarewells.backend.repository;

import com.lastfarewells.backend.entity.LastMessageCount;
import com.lastfarewells.backend.entity.MessageStatusEnum;
import com.lastfarewells.backend.entity.MessageTypeEnum;
import com.lastfarewells.backend.entity.Messages;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessagesRepository extends JpaRepository<Messages, Long> {

    Page<Messages> findAllByUserIdAndMessageType(Long userId, MessageTypeEnum messageType, Pageable pageable);

    List<Messages> findAllByDeliveryDateAndStatusNotIn(Date deliveryDate, List<MessageStatusEnum> status);

    Page<Messages> findAllByUserId(Long userId, Pageable pageable);

    @Query(value = "select "
        + " count(1) filter (where message_type = 'LETTER') as letterCount,"
        + " count(1) filter (where message_type = 'VIDEO') as videoCount,"
        + " count(1) filter (where message_type = 'AUDIO') as audioCount"
        + " from Messages where user_id= :userId", nativeQuery = true)
    LastMessageCount findMessageCountByUserId(@Param("userId") Long userId);

    List<Messages> findAllByDeliveryDate(Date deliveryDate);
    
    @Query(value = "SELECT * "
            + " from Messages where user_id= :userId and message_type= :messageType ORDER BY created_on DESC LIMIT 1", nativeQuery = true)
    Messages findOneByUserIdAndMessageType(Long userId, String messageType);
}
