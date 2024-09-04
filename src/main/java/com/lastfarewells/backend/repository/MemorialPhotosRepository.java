package com.lastfarewells.backend.repository;

import com.lastfarewells.backend.entity.MemorialPhotos;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemorialPhotosRepository extends JpaRepository<MemorialPhotos, Long> {

    List<MemorialPhotos> findByUserIdOrderBySortOrder(Long userId);

}
