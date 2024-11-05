package com.lastfarewells.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lastfarewells.backend.entity.DeliveryMethodEnum;
import com.lastfarewells.backend.entity.MessageStatusEnum;
import com.lastfarewells.backend.entity.MessageTypeEnum;
import com.lastfarewells.backend.entity.ScheduleTypeEnum;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessagesDto {

    @NotNull(message = "userId cannot be null")
    private Long                 userId;
    private MessengeRecipientDto recipient;
    private Long                 messenger;
    private MessageStatusEnum    status;
    @NotNull(message = "messageType cannot be null")
    private MessageTypeEnum      messageType;
    private String               title;
    private String               description;
    private String               content;
    private String               fileName;
    private Boolean              deliverOnDeath;
    private String               eventTitle;

    private DeliveryMethodEnum deliveryMethod;
    private ScheduleTypeEnum   scheduleType;
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date               deliveryDate;
    private Integer deliverYrsAfterDeath;

}
