package com.lastfarewells.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "failed_emails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FailedEmails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "notification_type")
	private String notificationType;
	@Column(name = "email_address")
	private String emailAddress;
	@Column(name = "action")
	private String action;
	@Column(name = "diagnostic_code")
	private String diagnosticCode;
	@Column(name = "subject")
	private String subject;
	@Column(name = "date")
	private Instant date;
	@Column(name = "created_on")
	private Instant createdOn;
	@Column(name = "updated_on")
	private Instant updatedOn;

}
