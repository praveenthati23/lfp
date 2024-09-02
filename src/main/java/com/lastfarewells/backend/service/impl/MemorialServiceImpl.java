package com.lastfarewells.backend.service.impl;

import com.lastfarewells.backend.dto.MemorialDto;
import com.lastfarewells.backend.entity.Memorial;
import com.lastfarewells.backend.exception.MemorialException;
import com.lastfarewells.backend.repository.MemorialRepository;
import com.lastfarewells.backend.service.MemorialService;
import java.time.Instant;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemorialServiceImpl implements MemorialService {

    private final MemorialRepository memorialRepository;
    private final ModelMapper        modelMapper;

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
        return memorialRepository.findByUserId(userId).orElseThrow(() -> new MemorialException("Memorial not found for user"));
    }


}
