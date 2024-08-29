package com.lastfarewells.backend.service.impl;

import java.util.concurrent.Future;

import org.springframework.stereotype.Service;

import com.lastfarewells.backend.client.SpotifyClient;
import com.lastfarewells.backend.exception.PlaylistException;
import com.lastfarewells.backend.service.PlaylistService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaylistServiceImpl implements PlaylistService {
	private final SpotifyApi SpotifyApi;
	private final SpotifyClient SpotifyClient;

	@Override
	public Paging<Track> searchTracks(String query) {
		try {
			SpotifyApi.setAccessToken(SpotifyClient.getSpotifyAccessToken());
			final SearchTracksRequest searchTracksRequest = SpotifyApi.searchTracks(query).build();
			Future<Paging<Track>> trackPagingFuture = searchTracksRequest.executeAsync();
			return trackPagingFuture.get();
		} catch (Exception e) {
			log.error("Exception while  searchTracks " + e.getMessage());
			throw new PlaylistException("Search Tracks API Failed");
		}
	}
}
