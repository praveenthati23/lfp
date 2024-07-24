package com.lastfarewells.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lastfarewells.backend.entity.PresignedUrl;
import com.lastfarewells.backend.repository.PresignedUrlRepository;

@Service
public class PresignedUrlService {
	
	@Autowired
	private PresignedUrlRepository presignedRepo;
	
	public void save(PresignedUrl presignedUrl) {
		presignedRepo.save(presignedUrl);
		
	}

	public PresignedUrl findByKey(String key) {
		return presignedRepo.findByKey(key);
		
	}
}
