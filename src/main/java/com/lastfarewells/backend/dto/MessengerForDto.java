package com.lastfarewells.backend.dto;

import java.time.Instant;
import java.util.Date;

public interface MessengerForDto {

    Long getId();

    String getFirstName();

    String getLastName();

    String getEmail();

    Boolean getIsDeceased();

    Long getUserId();

    Date getDeathDate();

    String getAttachment();

    String getAttachmentFilename();

    String getObituaryLink();

    Boolean getIsVerified();

    String getVerifiedBy();

    Instant getVerifiedAt();

    String getStatus();

    String getStatusNote();

    String getCustomNote();

    Instant getDeathReportCreatedOn();

    Instant getDeathReportUpdatedOn();

    Instant getDeclinedAt();

    String getDeclineReason();
}
