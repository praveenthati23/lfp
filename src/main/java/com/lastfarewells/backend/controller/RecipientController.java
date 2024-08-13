package com.lastfarewells.backend.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lastfarewells.backend.dto.RecipientDto;
import com.lastfarewells.backend.entity.Recipient;
import com.lastfarewells.backend.service.RecipientService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/recipients")
@AllArgsConstructor
public class RecipientController {

	private final RecipientService recipientService;

	@PostMapping()
	public ResponseEntity<?> createRecipient(@RequestBody @Valid RecipientDto recipientDto) {
		try {
			return new ResponseEntity<Recipient>(recipientService.createRecipient(recipientDto), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateRecipient(@PathVariable Long id,
			@RequestBody @Valid RecipientDto recipientDto) {
		try {
			return new ResponseEntity<String>(recipientService.updateRecipent(id, recipientDto), HttpStatus.OK);

		} catch (Exception ex) {
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{userId}")
	public Page<Recipient> findAllRecipient(@PathVariable Long userId, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return recipientService.findAllRecipient(PageRequest.of(page, size, Sort.by("id").descending()), userId);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteRecipient(@PathVariable Long id) {
		try {
			return new ResponseEntity<String>(recipientService.deleteRecipient(id), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
