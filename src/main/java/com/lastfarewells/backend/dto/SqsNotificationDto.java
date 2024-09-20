package com.lastfarewells.backend.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SqsNotificationDto {

	@JsonProperty("Message")
	private Message message;

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Message {
		@JsonProperty("notificationType")
		private String notificationType;
		@JsonProperty("bounce")
		private Bounce bounce;
		@JsonProperty("mail")
		private Object mail;
	}

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Bounce {
		private String feedbackId;
		private String bounceType;
		private String bounceSubType;
		@JsonProperty("bouncedRecipients")
		private List<BouncedRecipients> bouncedRecipients;
		private String timestamp;
		private String remoteMtaIp;
		private String reportingMTA;
	}

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class BouncedRecipients {
		@JsonProperty("emailAddress")
		private String emailAddress;
		@JsonProperty("action")
		private String action;
		@JsonProperty("status")
		private String status;
		@JsonProperty("diagnosticCode")
		private String diagnosticCode;
	}

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class CommonHeaders {
		@JsonProperty("from")
		private List<String> from;
		@JsonProperty("to")
		private List<String> to;
		@JsonProperty("date")
		private String date;
		@JsonProperty("subject")
		private String subject;
	}

}
