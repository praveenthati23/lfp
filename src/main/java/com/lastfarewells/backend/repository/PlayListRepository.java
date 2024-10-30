package com.lastfarewells.backend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lastfarewells.backend.entity.PlayList;

public interface PlayListRepository extends JpaRepository<PlayList, Long> {

	Page<PlayList> findAllByUserId(Pageable pageable, Long userId);

	List<PlayList> findAllByUserId(Long userId, Sort sort);

	int countByUserId(Long userId);
}
