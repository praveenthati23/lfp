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
import lombok.NoArgsConstructor;

@Entity
@Table(name = "playlists")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "user_id")
	private Long userId;
	@Column(name = "name")
	private String name;
	@Column(name = "external_id")
	private String externalId;
	@Column(name = "image_url", columnDefinition = "jsonb")
	private String imageUrl;
	@Column(name = "artist_name")
	private String artistName;
	@Column(name = "preview_url")
	private String previewUrl;
	@Column(name = "duration_ms")
	private Long durationMs;
	@Column(name = "created_on")
	private Instant createdOn;
	@Column(name = "updated_on")
	private Instant updatedOn;
	@Column(name = "sort_order")
	private Integer sortOrder;

}
