package com.lastfarewells.backend.service;

import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;

public interface PlaylistService {

	Paging<Track> searchTracks(String query);

}
