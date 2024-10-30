package com.lastfarewells.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayListDto {

	private Long id;
	private Long userId;
	private String name;
	private String externalId;
	private ImageDto imageUrl;
	private String artistName;
	private Long durationMs;
	private String previewUrl;
	private Integer sortOrder;

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ImageDto {
		private Integer height;
		private String url;
		private Integer width;
	}

}
