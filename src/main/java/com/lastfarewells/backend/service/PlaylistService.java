package com.lastfarewells.backend.service;

import org.springframework.data.domain.Page;

import com.lastfarewells.backend.dto.PlayListDto;

import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;

public interface PlaylistService {

	Paging<Track> searchTracks(String query);

	PlayListDto addPlaylist(String playlist);

	Page<PlayListDto> getPlayList(Long userId, int page, int size);

	String deletePlaylist(Long id);

}
