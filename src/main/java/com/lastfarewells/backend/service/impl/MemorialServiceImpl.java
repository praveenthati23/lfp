package com.lastfarewells.backend.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lastfarewells.backend.dto.MemorialDto;
import com.lastfarewells.backend.dto.PlayListDto;
import com.lastfarewells.backend.dto.PublicMemorialDto;
import com.lastfarewells.backend.dto.PublicMemorialDto.PublicPlaylist;
import com.lastfarewells.backend.dto.UserDetailsDto;
import com.lastfarewells.backend.entity.Memorial;
import com.lastfarewells.backend.entity.MessageTypeEnum;
import com.lastfarewells.backend.entity.Messages;
import com.lastfarewells.backend.entity.PlayList;
import com.lastfarewells.backend.entity.Users;
import com.lastfarewells.backend.exception.MemorialException;
import com.lastfarewells.backend.exception.MessengesException;
import com.lastfarewells.backend.repository.MemorialPhotosRepository;
import com.lastfarewells.backend.repository.MemorialRepository;
import com.lastfarewells.backend.repository.MessagesRepository;
import com.lastfarewells.backend.repository.PlayListRepository;
import com.lastfarewells.backend.repository.UsersRepository;
import com.lastfarewells.backend.service.MemorialService;

import io.jsonwebtoken.lang.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemorialServiceImpl implements MemorialService {

	private final MemorialRepository memorialRepository;
	private final ModelMapper modelMapper;
	private final UsersRepository userRepo;
	private final MemorialPhotosRepository memorialPhotosRepo;
	private final PlaylistServiceImpl playlistServiceImpl;
	private final MessagesRepository messageRepo;

	@Override
	public Memorial createMemorial(MemorialDto memorialDto) {
		if (memorialDto.getUserId() == null) {
			throw new MemorialException("userId is mandatory");
		}
		// check if memorial already exists for userId
		Optional<Memorial> existingMemorial = memorialRepository.findByUserId(memorialDto.getUserId());
		if (existingMemorial.isPresent()) {
			return existingMemorial.get();
		}

		Memorial memorial = modelMapper.map(memorialDto, Memorial.class);
		memorial.setCreatedOn(Instant.now());
		log.info("Saving new memorial for userId {}", memorialDto.getUserId());
		return memorialRepository.save(memorial);
	}

	@Override
	public Memorial getUserMemorial(Long userId) {
		Optional<Memorial> memorial = memorialRepository.findByUserId(userId);
		if (memorial.isPresent()) {
			return memorial.get();
		} else {
			return memorialRepository
					.save(Memorial.builder().userId(userId).createdOn(Instant.now()).isTributePage(true).build());
		}
	}

	@Override
	public Memorial updateMemorial(Long id, MemorialDto memorialDto) {
		Memorial memorial = memorialRepository.findById(id)
				.orElseThrow(() -> new MessengesException("Memorial request not found"));
		log.info("Updating Memorial {} for user {}", id, memorial.getUserId());

		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		modelMapper.map(memorialDto, memorial);
		memorial.setUserId(memorial.getUserId());
		memorial.setUpdatedOn(Instant.now());

		return memorialRepository.save(memorial);
	}

	@Override
	public void deleteMemorial(Long id) {
		Memorial memorial = memorialRepository.findById(id)
				.orElseThrow(() -> new MessengesException("Memorial request not found"));
		log.info("Deleting memorial with id : {}", id);
		memorialRepository.deleteById(memorial.getId());
	}

	@Override
	public Boolean getUserMemorial(String alias) {
		return !memorialRepository.findByAliasIgnoreCase(alias).isPresent();
	}

	@Override
	public PublicMemorialDto getPublicMemorials(String alias) {

		PublicMemorialDto response = PublicMemorialDto.builder().build();
		Memorial memorial = memorialRepository.findByAliasIgnoreCase(alias)
				.orElseThrow(() -> new MessengesException("Requested Alias not found"));
		Users user = userRepo.findById(memorial.getUserId()).get();
		if (!user.getDeceased())
			throw new MessengesException("Memorial page is not active");
		UserDetailsDto userDetailsDto = UserDetailsDto.builder().build();
		modelMapper.map(user, userDetailsDto);
		response.setUser(userDetailsDto);
		response.setPhotos(memorialPhotosRepo.findByUserIdOrderBySortOrder(memorial.getUserId()));
		PublicPlaylist playlist = PublicPlaylist.builder().build();
		playlist.setTracks(playlistServiceImpl.getPublicPlayList(memorial.getUserId()));
		playlist.setCount(playlist.getTracks()!=null?playlist.getTracks().size():0);
		response.setPlaylist(playlist);
		response.setMemorial_page_id(memorial.getId());
		response.setUserId(memorial.getUserId());
		response.setBackground_image(memorial.getBackgroundImage());
		response.setHeadshot(memorial.getHeadshot());
		response.setMemorial_name(memorial.getAlias());
		response.setEpitaph(memorial.getEpitaph());
		response.setObituary(memorial.getObituary());
		response.setBirthday(userDetailsDto.getBirthDate());
		Messages videoMessage = messageRepo.findOneByUserIdAndMessageType(memorial.getUserId(), MessageTypeEnum.VIDEO.name());
		if (videoMessage != null)
			response.setLast_message(videoMessage.getFileName());
		
		return response;

	}

}
