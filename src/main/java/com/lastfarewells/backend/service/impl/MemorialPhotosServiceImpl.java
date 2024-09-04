package com.lastfarewells.backend.service.impl;

import com.lastfarewells.backend.dto.MemorialPhotosDto;
import com.lastfarewells.backend.entity.MemorialPhotos;
import com.lastfarewells.backend.repository.MemorialPhotosRepository;
import com.lastfarewells.backend.service.MemorialPhotosService;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemorialPhotosServiceImpl implements MemorialPhotosService {

    private final MemorialPhotosRepository memorialPhotosRepository;
    private final ModelMapper              modelMapper;

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


}
