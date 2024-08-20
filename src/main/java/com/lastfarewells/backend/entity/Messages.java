package com.lastfarewells.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "user_id")
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "recipient_id", referencedColumnName = "id", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Recipient recipient;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "messenger_id", referencedColumnName = "id", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Messenger messenger;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private MessageStatusEnum status;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_type")
    private MessageTypeEnum messageType;

    @Column(name = "title")
    private String  title;
    @Column(name = "description")
    private String  description;
    @Column(name = "content")
    private String  content;
    @Column(name = "file_name")
    private String  fileName;
    @Column(name = "deliver_on_death")
    private Boolean deliverOnDeath;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_method")
    private DeliveryMethodEnum deliveryMethod;
    @Enumerated(EnumType.STRING)
    @Column(name = "schedule_type")
    private ScheduleTypeEnum   scheduleType;
    @Column(name = "delivery_date")
    private Date               deliveryDate;
    @Column(name = "event_title")
    private String  eventTitle;

    @Column(name = "created_on")
    private Instant createdOn;
    @Column(name = "updated_on")
    private Instant updatedOn;

}
