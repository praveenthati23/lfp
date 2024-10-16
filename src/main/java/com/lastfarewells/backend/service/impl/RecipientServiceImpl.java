package com.lastfarewells.backend.service.impl;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.lastfarewells.backend.constants.LFareWellConstants;
import com.lastfarewells.backend.dto.RecipientDto;
import com.lastfarewells.backend.entity.Recipient;
import com.lastfarewells.backend.exception.RecipientException;
import com.lastfarewells.backend.repository.RecipientRepository;
import com.lastfarewells.backend.repository.UsersRepository;
import com.lastfarewells.backend.service.RecipientService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecipientServiceImpl implements RecipientService {

	private final RecipientRepository recipientRepository;
	private final UsersRepository userRepository;

	@Override
	public Recipient createRecipient(RecipientDto recipientDto) {
		log.info("creating Recipient");
		Optional<Recipient> existingRecipient = recipientRepository
				.findByEmailAndUserIdAndIsUserRecipient(recipientDto.getEmail(), recipientDto.getUserId(), true);

		if (existingRecipient.isPresent()) {
			throw new RecipientException(LFareWellConstants.RECIPIENT_DUPLICATE_EMAIL_MSG);
		}

		Recipient recipient = Recipient.builder().firstName(recipientDto.getFirstName())
				.lastName(recipientDto.getLastName()).email(recipientDto.getEmail()).isUserRecipient(true)
				.relationship(recipientDto.getRelationship().getValue())
				.userId(recipientDto.getUserId()).createdOn(Instant.now()).updatedOn(Instant.now()).build();

		recipientRepository.save(recipient);
		return recipient;
	}

	@Override
	public String updateRecipent(Long id, RecipientDto recipientDto) {

		log.info("updating Recipient: " + id);
		Recipient recipient = recipientRepository.findById(id)
				.orElseThrow(() -> new RecipientException(LFareWellConstants.RECIPIENT_INVALID_MSG));

		Optional<Recipient> existingRecipient = recipientRepository
				.findByEmailAndUserIdAndIsUserRecipient(recipientDto.getEmail(), recipientDto.getUserId(), true);

		if (existingRecipient.isPresent() && !existingRecipient.get().getId().equals(id)) {
			throw new RecipientException(LFareWellConstants.RECIPIENT_DUPLICATE_EMAIL_MSG);
		}

		recipient.setUpdatedOn(Instant.now());
		recipient.setFirstName(recipientDto.getFirstName());
		recipient.setLastName(recipientDto.getLastName());
		recipient.setEmail(recipientDto.getEmail());
		recipient.setRelationship(recipientDto.getRelationship().getValue());
		recipientRepository.save(recipient);
		return LFareWellConstants.RECIPIENT_UPDATE_SUCCESS_MSG;
	}

	@Override
	public Page<Recipient> findAllRecipient(PageRequest pageRequest, Long userId) {
		log.info("Fetch All Recipients");
		return recipientRepository.findAllByUserIdAndIsUserRecipient(userId, true, pageRequest);
	}

	@Override
	public String deleteRecipient(Long id) {

		log.info("delete the Recipient: " + id);
		recipientRepository.deleteById(id);
		return LFareWellConstants.RECIPIENT_UPDATE_DELETE_MSG;

	}

}
