package com.lastfarewells.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "death_report")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeathReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "messenger_id")
    private Long    messengerId;
    @Column(name = "death_date")
    private Date    deathDate;
    @Column(name = "attachment")
    private String  attachment;
    @Column(name = "attachment_filename")
    private String  attachmentFilename;
    @Column(name = "obituary_link")
    private String  obituaryLink;
    @Column(name = "is_verified")
    private Boolean isVerified;
    @Column(name = "verified_by")
    private Long    verifiedBy;
    @Column(name = "verified_at")
    private Instant verifiedAt;
    @Column(name = "status")
    private String  status;
    @Column(name = "status_note")
    private String  statusNote;
    @Column(name = "custom_note")
    private String  customNote;
    @Column(name = "created_on")
    private Instant createdOn;
    @Column(name = "updated_on")
    private Instant updatedOn;

}
