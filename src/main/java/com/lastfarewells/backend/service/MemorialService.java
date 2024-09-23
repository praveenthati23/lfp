package com.lastfarewells.backend.service;

import com.lastfarewells.backend.dto.MemorialDto;
import com.lastfarewells.backend.entity.Memorial;

public interface MemorialService {

    Memorial createMemorial(MemorialDto memorialDto);

    Memorial getUserMemorial(Long userId);

    Memorial updateMemorial(Long id, MemorialDto memorialDto);

    void deleteMemorial(Long id);

    Boolean getUserMemorial(String alias);

}
