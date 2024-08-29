package com.lastfarewells.backend.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastfarewells.backend.constants.LFareWellConstants;
import com.lastfarewells.backend.exception.PlaylistException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpotifyClient {

	@Value("${spotify.client_id}")
	private String clientId;
	@Value("${spotify.client_secret}")
	private String clientSecret;
	@Value("${spotify.tokenUrl}")
	private String tokenUrl;
	private final RestTemplate restTemplate;

	public String getSpotifyAccessToken() {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("grant_type", LFareWellConstants.CLIENT_CREDENTIALS);
			map.add("client_id", clientId);
			map.add("client_secret", clientSecret);
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map,
					headers);
			ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node;
			node = mapper.readTree(response.getBody().toString());
			return node.get(LFareWellConstants.ACCESS_TOKEN).asText();
		} catch (Exception e) {
			log.error("Exception while get SpotifyAccessToken " + e.getMessage());
			throw new PlaylistException("Get Spotify Access Token Failed");
		}

	}
}
