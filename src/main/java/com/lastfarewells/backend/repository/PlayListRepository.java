package com.lastfarewells.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lastfarewells.backend.entity.PlayList;

public interface PlayListRepository extends JpaRepository<PlayList, Long> {

	Page<PlayList> findAllByUserId(Pageable pageable, Long userId);

}
