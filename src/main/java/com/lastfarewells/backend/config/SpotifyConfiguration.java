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
	@Value("${spotify.redirect_uri}")
	private String redirectUri;

	@Bean
	public SpotifyApi getSpotifyObject() {
		URI redirectedURL = SpotifyHttpManager.makeUri(redirectUri);
		return new SpotifyApi.Builder().setClientId(clientId).setClientSecret(clientSecret)
				.setRedirectUri(redirectedURL).build();

	}
}
