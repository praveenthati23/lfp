package com.lastfarewells.backend.dto;

import java.util.Date;
import java.util.List;

import com.lastfarewells.backend.entity.MemorialPhotos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicMemorialDto {

    private Long    userId;
    private Long  memorial_page_id;
    private String  background_image;
    private String  last_message;
    private Date  birthday;
    private String  memorial_name;
    private String  headshot;
    private String  epitaph;
    private String  obituary;
    private UserDetailsDto user;
    private List<MemorialPhotos> photos;
    private PublicPlaylist playlist;
    
    
    @Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class PublicPlaylist {
		private List<PlayListDto> tracks;
		private int count;
	}


}
