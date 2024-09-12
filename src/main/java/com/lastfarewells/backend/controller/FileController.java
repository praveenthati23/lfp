package com.lastfarewells.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lastfarewells.backend.domain.presigUrlDownloadResult;
import com.lastfarewells.backend.domain.presigUrlUploadResult;
import com.lastfarewells.backend.entity.PresignedUrl;
import com.lastfarewells.backend.service.StorjAWSService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/file")
@AllArgsConstructor
@Slf4j
public class FileController {

	@Autowired
	private StorjAWSService storjService;

	@PostMapping("/upload")
	public ResponseEntity<presigUrlUploadResult> getUploadUrl(@RequestParam(required = false) Boolean isNew,
			@RequestBody PresignedUrl presignedUrlObj) throws Exception {
		// Body
		// {"directory":"audios","fileType":"audio/mpeg","name":"Recorded
		// Audio","size":"82705","extension":"mpga"}

		return new ResponseEntity<presigUrlUploadResult>(storjService.getUploadUrl(presignedUrlObj), HttpStatus.OK);

	}

	@GetMapping("/download")
	public ResponseEntity<presigUrlDownloadResult> getDownloadUrl(@RequestParam(required = true) String fileName)
			throws Exception {
		return new ResponseEntity<presigUrlDownloadResult>(storjService.getDownloadUrl(fileName), HttpStatus.OK);
	}

	@DeleteMapping("")
	public ResponseEntity<String> deleteFile(@RequestParam(required = true) String fileName) throws Exception {
		return new ResponseEntity<String>(storjService.deleteFile(fileName), HttpStatus.OK);

	}

}
