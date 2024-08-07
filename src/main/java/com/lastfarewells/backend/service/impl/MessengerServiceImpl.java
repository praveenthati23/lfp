package com.lastfarewells.backend.service.impl;

import com.lastfarewells.backend.dto.MessengerRequestDto;
import com.lastfarewells.backend.dto.MessengerResendDto;
import com.lastfarewells.backend.dto.MessengerVerificationDto;
import com.lastfarewells.backend.entity.Messenger;
import com.lastfarewells.backend.exception.MessengerException;
import com.lastfarewells.backend.exception.UserException;
import com.lastfarewells.backend.repository.MessengerRepository;
import com.lastfarewells.backend.service.MessengerService;
import com.lastfarewells.backend.utils.JWTUtils;
import java.time.Instant;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessengerServiceImpl implements MessengerService {

    private final MessengerRepository messengerRepository;

    @Override
    public Messenger createMessenger(MessengerRequestDto messengerRequestDto) {
        // Validate same request does not exists
        Optional<Messenger> existingMessengerForRequestor = messengerRepository.findByMessengerForAndEmail(messengerRequestDto.getRequestorId(),
            messengerRequestDto.getEmail());
        if (existingMessengerForRequestor.isPresent()) {
            throw new UserException("The email address already exists as your messenger.");
        }

        String token = JWTUtils.generateVerificationToken(messengerRequestDto.getEmail());

        Messenger messenger = Messenger.builder().messengerFor(messengerRequestDto.getRequestorId())
            .firstName(messengerRequestDto.getFirstName()).lastName(messengerRequestDto.getLastName())
            .email(messengerRequestDto.getEmail()).isConfirmed(false).invitationToken(token)
            .createdOn(Instant.now()).build();
        messengerRepository.save(messenger);

        //TODO Send email to Messenger

        return messenger;
    }

    @Override
    public Page<Messenger> findAllMessenger(PageRequest pageRequest, Long userId) {
        return messengerRepository.findAllByMessengerFor(userId, pageRequest);
    }

    @Override
    public Messenger updateMessenger(Long id, MessengerRequestDto messengerRequestDto) {
        Messenger messenger = messengerRepository.findById(id)
            .orElseThrow(() -> new MessengerException("Messenger request not found"));
        String existingEmail = messenger.getEmail();

        messenger.setUpdatedOn(Instant.now());
        messenger.setFirstName(messengerRequestDto.getFirstName());
        messenger.setLastName(messengerRequestDto.getLastName());
        messenger.setEmail(messengerRequestDto.getEmail());

        messengerRepository.save(messenger);
        if (existingEmail.equalsIgnoreCase(messenger.getEmail())) {
            //TODO Send email to Messenger
        }
        return messenger;
    }

    @Override
    public Messenger verifyMessengerToken(MessengerVerificationDto messengerRequestDto) {
        return messengerRepository.findByInvitationToken(messengerRequestDto.getInvitationToken())
            .orElseThrow(() -> new MessengerException("Invitation token not found"));
    }

    @Override
    public void resendMessengerInvitation(MessengerResendDto messengerResendDto) {
        Messenger messenger = messengerRepository.findById(messengerResendDto.getId())
            .orElseThrow(() -> new MessengerException("Messenger request not found"));
        log.info("Resending invitation for {} by userId : {}", messenger.getEmail(), messenger.getMessengerFor());

        String token = JWTUtils.generateVerificationToken(messenger.getEmail());
        messenger.setInvitationToken(token);

        messengerRepository.save(messenger);

        //TODO Send email to Messenger

    }

}
