package com.lastfarewells.backend.service;

import com.lastfarewells.backend.dto.MemorialPhotosDto;
import com.lastfarewells.backend.entity.MemorialPhotos;
import java.util.List;

public interface MemorialPhotosService {

    MemorialPhotos createMemorialPhotos(MemorialPhotosDto memorialPhotosDto);

    List<MemorialPhotos> findAllMemorialPhotos(Long userId);

    MemorialPhotos updateMemorialPhotos(Long id, MemorialPhotosDto memorialPhotosDto);

    void deleteMemorialPhotos(Long id);

}
