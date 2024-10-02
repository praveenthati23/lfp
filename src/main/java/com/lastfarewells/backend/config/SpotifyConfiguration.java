package com.lastfarewells.backend.config;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

@Configuration
public class SpotifyConfiguration {

	@Value("${spotify.client_id}")
	private String clientId;
	@Value("${spotify.client_secret}")
	private String clientSecret;

	@Bean
	public SpotifyApi getSpotifyObject() {
		return new SpotifyApi.Builder().setClientId(clientId).setClientSecret(clientSecret).build();

	}
}
