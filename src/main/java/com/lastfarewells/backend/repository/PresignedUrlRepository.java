package com.lastfarewells.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lastfarewells.backend.entity.PresignedUrl;

public interface PresignedUrlRepository extends JpaRepository<PresignedUrl, Long> {

	PresignedUrl findByKey(String key);


}
