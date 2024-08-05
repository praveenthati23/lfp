package com.lastfarewells.backend.dto;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionDto {

    private Long    subscriptionId;
    private String  name;
    private Instant createdOn;
    private Instant updatedOn;

}
