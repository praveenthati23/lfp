package com.lastfarewells.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

	private final PlaylistService SpotifyService;

	@GetMapping("/search")
	public Paging<Track> searchTracks(@RequestParam String query) {
		return SpotifyService.searchTracks(query);
	}

}
