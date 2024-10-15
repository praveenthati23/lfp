package com.lastfarewells.backend.dto;

import com.lastfarewells.backend.entity.DeliveryMethodEnum;
import com.lastfarewells.backend.entity.MessageStatusEnum;
import com.lastfarewells.backend.entity.MessageTypeEnum;
import com.lastfarewells.backend.entity.Messenger;
import com.lastfarewells.backend.entity.Recipient;
import com.lastfarewells.backend.entity.ScheduleTypeEnum;
import java.time.Instant;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessagesResponseDto {

    private Long               id;
    private Long               userId;
    private Recipient          recipient;
    private Messenger          messenger;
    private MessageStatusEnum  status;
    private MessageTypeEnum    messageType;
    private String             title;
    private String             description;
    private String             content;
    private String             fileName;
    private Boolean            deliverOnDeath;
    private DeliveryMethodEnum deliveryMethod;
    private ScheduleTypeEnum   scheduleType;
    private Date               deliveryDate;
    private String             eventTitle;
    private Instant            createdOn;
    private Instant            updatedOn;
    private Boolean            isFirstMessage;

}
