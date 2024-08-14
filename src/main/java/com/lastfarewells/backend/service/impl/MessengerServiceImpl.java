package com.lastfarewells.backend.service.impl;

import com.lastfarewells.backend.constants.LFareWellConstants;
import com.lastfarewells.backend.dto.EmailMessage;
import com.lastfarewells.backend.dto.MessengerActionDto;
import com.lastfarewells.backend.dto.MessengerForDto;
import com.lastfarewells.backend.dto.MessengerRequestDto;
import com.lastfarewells.backend.dto.MessengerResendDto;
import com.lastfarewells.backend.dto.MessengerVerificationDto;
import com.lastfarewells.backend.dto.MessengerVerificationResponseDto;
import com.lastfarewells.backend.dto.MessengerVerificationResponseDto.MessengerForDetails;
import com.lastfarewells.backend.entity.Messenger;
import com.lastfarewells.backend.entity.Users;
import com.lastfarewells.backend.exception.MessengerException;
import com.lastfarewells.backend.exception.UserException;
import com.lastfarewells.backend.repository.MessengerRepository;
import com.lastfarewells.backend.repository.UsersRepository;
import com.lastfarewells.backend.service.EmailService;
import com.lastfarewells.backend.service.MessengerService;
import com.lastfarewells.backend.utils.JWTUtils;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessengerServiceImpl implements MessengerService {

    private final MessengerRepository messengerRepository;
    private final UsersRepository     usersRepository;
    private final EmailService        emailService;

    @Value("${spring.mail.from}")
    private String fromAddress;

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
      //  messengerRepository.save(messenger);

        //TODO Send email to Messenger

        Map<String, Object> props = new HashedMap<>();
        props.put(LFareWellConstants.MESSENGER,
            messenger.getFirstName() + (StringUtils.isNotEmpty(messenger.getLastName()) ? " " + messenger.getLastName() : ""));
        props.put(LFareWellConstants.USER_NAME, JWTUtils.getCurrentUserSub());
        props.put(LFareWellConstants.TOKEN, token);

        EmailMessage emailMsg = emailBuilder(fromAddress, messenger.getEmail(),
            LFareWellConstants.MESSENGER_INVITATION_SUBJECT, LFareWellConstants.MESSENGER_INVITATION_TEMPLATE, props);

        emailService.sendEmail(emailMsg);

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
    public MessengerVerificationResponseDto verifyMessengerToken(MessengerVerificationDto messengerRequestDto) {
        Messenger messenger = messengerRepository.findByInvitationToken(messengerRequestDto.getInvitationToken())
            .orElseThrow(() -> new MessengerException("Invitation token not found"));

        Users messengerForUser = usersRepository.findById(messenger.getMessengerFor())
            .orElseThrow(() -> new UserException("User not found"));

        Optional<Users> messengerUser = usersRepository.findByEmail(messenger.getEmail());

        MessengerForDetails messengerFor = MessengerForDetails.builder().firstName(messengerForUser.getFirstName())
            .lastName(messengerForUser.getLastName()).userId(messengerForUser.getId()).build();
        return MessengerVerificationResponseDto.builder().email(messenger.getEmail()).firstName(messenger.getFirstName())
            .lastName(messenger.getLastName()).messengerFor(messengerFor).isNewUser(!messengerUser.isPresent()).build();
    }

    @Override
    public void resendMessengerInvitation(MessengerResendDto messengerResendDto) {
        Messenger messenger = messengerRepository.findById(messengerResendDto.getId())
            .orElseThrow(() -> new MessengerException("Messenger request not found"));
        log.info("Resending invitation for {} by userId : {}", messenger.getEmail(), messenger.getMessengerFor());

        String token = JWTUtils.generateVerificationToken(messenger.getEmail());
        messenger.setInvitationToken(token);
        messenger.setUpdatedOn(Instant.now());

        messengerRepository.save(messenger);

        //TODO Send email to Messenger

    }

    @Override
    public void acceptInvitation(MessengerActionDto messengerActionDto) {
        Messenger messenger = messengerRepository.findByInvitationToken(messengerActionDto.getInvitationToken())
            .orElseThrow(() -> new MessengerException("Invitation token not found"));

        if (!messengerActionDto.getIsAccepted()) {
            log.info("Messenger {} has declined the request for user {}", messenger.getEmail(), messenger.getMessengerFor());
            messengerRepository.delete(messenger);
            // TODO send decline mail to MessengerFor
        } else {
            log.info("Messenger {} has accepted the request for user {}", messenger.getEmail(), messenger.getMessengerFor());
            messenger.setIsConfirmed(true);
            messenger.setUpdatedOn(Instant.now());
            messenger.setInvitationToken(StringUtils.EMPTY);

            Optional<Users> messengerUser = usersRepository.findByEmail(messenger.getEmail());
            if (messengerUser.isPresent()) {
                messenger.setMessengerUserId(messengerUser.get().getId());
            }
            messengerRepository.save(messenger);

            // TODO thank you mail to messenger
            // TODO acceptance mail to MessengerFor
        }
    }

    @Override
    public Page<MessengerForDto> findAllUsersForMessengerfor(PageRequest pageRequest, Long userId) {
        return messengerRepository.findAllMessengerForUsers(userId, pageRequest);
    }

    private EmailMessage emailBuilder(String from, String to, String subject,
        String templateName, Map<String, Object> props) {
       /* Map<String, Object> props  =new HashedMap<>();
        props.put(LFareWellConstants.USER_NAME, userName);
        props.put(LFareWellConstants.TOKEN, token);
        props.put(LFareWellConstants.SUBJECT, subject);*/
        return new EmailMessage(from, to, subject, templateName, props);
    }

}
