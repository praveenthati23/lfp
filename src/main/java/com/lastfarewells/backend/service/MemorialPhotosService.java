package com.lastfarewells.backend.service;

import com.lastfarewells.backend.dto.MemorialPhotosDto;
import com.lastfarewells.backend.entity.MemorialPhotos;
import java.util.List;

public interface MemorialPhotosService {

    MemorialPhotos createMemorialPhotos(MemorialPhotosDto memorialPhotosDto);

    List<MemorialPhotos> findAllMemorialPhotos(Long userId);

}
