package com.lastfarewells.backend.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastfarewells.backend.client.SpotifyClient;
import com.lastfarewells.backend.constants.LFareWellConstants;
import com.lastfarewells.backend.dto.PlayListDto;
import com.lastfarewells.backend.dto.PlayListDto.ImageDto;
import com.lastfarewells.backend.entity.PlayList;
import com.lastfarewells.backend.entity.Users;
import com.lastfarewells.backend.exception.PlaylistException;
import com.lastfarewells.backend.repository.PlayListRepository;
import com.lastfarewells.backend.repository.UsersRepository;
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
	private final PlayListRepository playListRepository;
	private final UsersRepository usersRepo;
	private static final ObjectMapper objectMapper = new ObjectMapper();

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

	@Override
	public PlayListDto addPlaylist(String playlist) {
		JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext()
				.getAuthentication();
		String userEmail = authentication.getTokenAttributes().get("email").toString();
		Users user = usersRepo.findByEmail(userEmail).get();
		int playListSize = playListRepository.countByUserId(user.getId());
		try {
			JsonNode jsonNode = objectMapper.readTree(playlist);
			String id = jsonNode.get(LFareWellConstants.ID).asText();
			String name = jsonNode.get(LFareWellConstants.NAME).asText();
			Long durationMs = jsonNode.get(LFareWellConstants.DURATION_MS).asLong();
			String previewUrl = jsonNode.get(LFareWellConstants.PREVIEW_URL).asText();
			JsonNode artists = jsonNode.get(LFareWellConstants.ARTISTS).get(0);
			String artistName = artists.get(LFareWellConstants.NAME).asText();
			String imageStr = jsonNode.get(LFareWellConstants.ALBUM).get(LFareWellConstants.IMAGES).get(1)
					.toPrettyString();
			PlayList playListObj = PlayList.builder().userId(user.getId()).name(name).externalId(id).imageUrl(imageStr)
					.artistName(artistName).previewUrl(previewUrl).durationMs(durationMs).createdOn(Instant.now())
					.sortOrder(playListSize + 1).updatedOn(Instant.now()).build();
			PlayList savedPlayList = playListRepository.save(playListObj);
			return playListDtoMapper(savedPlayList);

		} catch (Exception e) {
			log.error("Exception while Adding PlayList: " + e.getMessage());
			throw new PlaylistException("Add PlayList Failed!");
		}
	}

	private PlayListDto playListDtoMapper(PlayList playListObj) {
		try {
			ImageDto imageDto = objectMapper.readValue(playListObj.getImageUrl(), ImageDto.class);
			PlayListDto playlistDto = PlayListDto.builder().id(playListObj.getId()).name(playListObj.getName())
					.userId(playListObj.getUserId()).externalId(playListObj.getExternalId())
					.artistName(playListObj.getArtistName()).durationMs(playListObj.getDurationMs())
					.sortOrder(playListObj.getSortOrder()).previewUrl(playListObj.getPreviewUrl()).imageUrl(imageDto)
					.build();
			return playlistDto;
		} catch (Exception e) {
			log.error("Exception while Playlistdto mapper: " + e.getMessage());
		}
		return null;
	}

	@Override
	public Page<PlayListDto> getPlayList(Long userId, int page, int size) {
		Sort sort = Sort.by(Sort.Direction.ASC, "sortOrder");
		Page<PlayListDto> playlists = playListRepository.findAllByUserId(PageRequest.of(page, size, sort), userId)
				.map(entity -> {
					PlayListDto dto = playListDtoMapper(entity);
					return dto;
				});
		return playlists;
	}

	@Override
	public String deletePlaylist(Long id) {
		JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext()
				.getAuthentication();
		String userEmail = authentication.getTokenAttributes().get("email").toString();
		Users user = usersRepo.findByEmail(userEmail).get();
		
		playListRepository.deleteById(id);
		Sort sort = Sort.by(Sort.Direction.ASC, "sortOrder");
		List<PlayList> playList = playListRepository.findAllByUserId(user.getId(), sort);
		for (int i = 0; i < playList.size(); i++) {
			playList.get(i).setSortOrder(i + 1);
		}
		playListRepository.saveAll(playList);
		return "Deleted PlayList id: " + id + " Successfully";
	}

	@Override
	public List<PlayListDto> reorder(List<Long> ids, Long userId) {
		Sort sort = Sort.by(Sort.Direction.ASC, "sortOrder");
		List<PlayList> playList = playListRepository.findAllByUserId(userId, sort);
		for (Long id : ids) {
			playList.stream().filter(item -> item.getId().equals(id))
					.forEach(item -> item.setSortOrder(ids.indexOf(id) + 1));
		}
		playListRepository.saveAll(playList);

		playList = playList.stream().sorted(Comparator.comparingInt(PlayList::getSortOrder))
				.collect(Collectors.toList());
		List<PlayListDto> reorderedList = new ArrayList<>();
		for (PlayList obj : playList) {
			reorderedList.add(playListDtoMapper(obj));
		}
		return reorderedList;
	}

}
