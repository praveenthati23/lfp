package com.lastfarewells.backend.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lastfarewells.backend.dto.PlayListDto;
import com.lastfarewells.backend.service.PlaylistService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;

@RestController
@RequestMapping("/api/v1/last-playlist")
@RequiredArgsConstructor
@Slf4j
public class PlaylistController {

	private final PlaylistService playlistService;

	@GetMapping("/search")
	public Paging<Track> searchTracks(@RequestParam String query) {
		return playlistService.searchTracks(query);
	}

	@PostMapping()
	public PlayListDto addPlayist(@RequestBody String playlist) {
		return playlistService.addPlaylist(playlist);
	}

	@GetMapping("/{userId}")
	public Page<PlayListDto> getPlayList(@PathVariable Long userId, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return playlistService.getPlayList(userId, page, size);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePlaylist(@PathVariable Long id) {
		try {
			return new ResponseEntity<String>(playlistService.deletePlaylist(id), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
