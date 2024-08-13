package com.lastfarewells.backend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.lastfarewells.backend.dto.RecipientDto;
import com.lastfarewells.backend.entity.Recipient;

public interface RecipientService {

	Recipient createRecipient(RecipientDto recipientDto);

	String updateRecipent(Long id, RecipientDto recipientDto);

	Page<Recipient> findAllRecipient(PageRequest pageRequest, Long userId);

	String deleteRecipient(Long id);

}
