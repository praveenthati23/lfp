package com.lastfarewells.backend.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "presigned_url_tbl")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PresignedUrl {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "directory")
	private String directory;
	@Column(name = "file_type")
	private String fileType;
	@Column(name = "name")
	private String name;
	@Column(name = "size")
	private Integer size;
	@Column(name = "extension")
	private String extension;
	@Column(name = "key")
	private String key;
	@Column(name = "created_at")
	private Instant createdAt;
	@Column(name = "modified_at")
	private Instant modifiedAt;
	@Column(name = "duration")
	private Double duration;

}
