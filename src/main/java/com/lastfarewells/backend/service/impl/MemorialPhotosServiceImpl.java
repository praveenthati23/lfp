package com.lastfarewells.backend.service.impl;

import com.lastfarewells.backend.dto.MemorialPhotosDto;
import com.lastfarewells.backend.entity.MemorialPhotos;
import com.lastfarewells.backend.exception.MemorialException;
import com.lastfarewells.backend.repository.MemorialPhotosRepository;
import com.lastfarewells.backend.service.MemorialPhotosService;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemorialPhotosServiceImpl implements MemorialPhotosService {

    private final MemorialPhotosRepository memorialPhotosRepository;

    @Override
    public MemorialPhotos createMemorialPhotos(MemorialPhotosDto memorialPhotosDto) {
        MemorialPhotos memorialPhotos = MemorialPhotos.builder().userId(memorialPhotosDto.getUserId())
            .filename(memorialPhotosDto.getFilename()).altText(memorialPhotosDto.getAltText())
            .caption(memorialPhotosDto.getCaption()).sortOrder(memorialPhotosDto.getSortOrder()).createdOn(Instant.now()).build();

        log.info("Saving new memorial photo for userId {} with caption {}", memorialPhotosDto.getUserId(), memorialPhotosDto.getCaption());
        return memorialPhotosRepository.save(memorialPhotos);
    }

    @Override
    public List<MemorialPhotos> findAllMemorialPhotos(Long userId) {
        return memorialPhotosRepository.findByUserIdOrderBySortOrder(userId);
    }

    @Override
    public MemorialPhotos updateMemorialPhotos(Long id, MemorialPhotosDto memorialPhotosDto) {
        MemorialPhotos memorialPhotos = memorialPhotosRepository.findById(id)
            .orElseThrow(() -> new MemorialException("MemorialPhotos request not found"));
        log.info("Updating MemorialPhotos {} for user {}", id, memorialPhotosDto.getUserId());

        if (StringUtils.isNotEmpty(memorialPhotosDto.getFilename())) {
            memorialPhotos.setFilename(memorialPhotosDto.getFilename());
        }
        if (StringUtils.isNotEmpty(memorialPhotosDto.getAltText())) {
            memorialPhotos.setAltText(memorialPhotosDto.getAltText());
        }
        if (StringUtils.isNotEmpty(memorialPhotosDto.getCaption())) {
            memorialPhotos.setCaption(memorialPhotosDto.getCaption());
        }
        if (memorialPhotosDto.getSortOrder() != 0) {
            memorialPhotos.setSortOrder(memorialPhotosDto.getSortOrder());
        }
        memorialPhotos.setUpdatedOn(Instant.now());

        return memorialPhotosRepository.save(memorialPhotos);
    }

    @Override
    public void deleteMemorialPhotos(Long id) {
        MemorialPhotos memorialPhotos = memorialPhotosRepository.findById(id)
            .orElseThrow(() -> new MemorialException("MemorialPhotos request not found"));
        log.info("Deleting MemorialPhotos with id : {}", id);
        memorialPhotosRepository.deleteById(memorialPhotos.getId());
    }


}
