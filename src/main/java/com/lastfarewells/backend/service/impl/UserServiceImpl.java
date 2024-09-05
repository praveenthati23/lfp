package com.lastfarewells.backend.service.impl;


import com.lastfarewells.backend.constants.LFareWellConstants;
import com.lastfarewells.backend.dto.EmailMessage;
import com.lastfarewells.backend.dto.LoginDto;
import com.lastfarewells.backend.dto.MessageCountDto;
import com.lastfarewells.backend.dto.PasswordResetDto;
import com.lastfarewells.backend.dto.SignupDto;
import com.lastfarewells.backend.dto.SubscriptionDto;
import com.lastfarewells.backend.dto.SubscriptionDto.AudioSettings;
import com.lastfarewells.backend.dto.SubscriptionDto.Feature;
import com.lastfarewells.backend.dto.SubscriptionDto.MediaData;
import com.lastfarewells.backend.dto.SubscriptionDto.Plan;
import com.lastfarewells.backend.dto.SubscriptionDto.VideoSettings;
import com.lastfarewells.backend.dto.UpdateUserDto;
import com.lastfarewells.backend.dto.UserAccessTokenDto;
import com.lastfarewells.backend.dto.UserDetailsDto;
import com.lastfarewells.backend.dto.VerifyEmailDto;
import com.lastfarewells.backend.entity.LastMessageCount;
import com.lastfarewells.backend.entity.Messenger;
import com.lastfarewells.backend.entity.Users;
import com.lastfarewells.backend.exception.UserAuthenticationException;
import com.lastfarewells.backend.exception.UserException;
import com.lastfarewells.backend.repository.MessagesRepository;
import com.lastfarewells.backend.repository.MessengerRepository;
import com.lastfarewells.backend.repository.UsersRepository;
import com.lastfarewells.backend.service.EmailService;
import com.lastfarewells.backend.service.IAMService;
import com.lastfarewells.backend.service.UserService;
import com.lastfarewells.backend.utils.JWTUtils;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.representations.AccessTokenResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UsersRepository     usersRepository;
    private final IAMService          keycloakService;
    private final ModelMapper         modelMapper;
    private final EmailService        emailService;
    private final MessengerRepository messengerRepository;
    private final MessagesRepository  messagesRepository;

    @Value("${spring.mail.from}")
    private String fromAddress;

    /*
     * @Override public Users registerUser(RegisterUserDto registerUserDto) {
     * log.info("Registering User with IAM id : {}", registerUserDto.getIamId());
     * Users users = Users.builder().firstName(registerUserDto.getFirstName())
     * .lastName(registerUserDto.getLastName()).iamId(registerUserDto.getIamId()).
     * birthDate(registerUserDto.getBirthDate()) .createdOn(Instant.now()).build();
     * return usersRepository.save(users); }
     */
    @Override
    @Transactional
    public void registerUser(SignupDto signupDto) {
        Optional<Users> existingUserAuth = usersRepository.findByEmail(signupDto.getEmail());
        if (existingUserAuth.isPresent()) {
            throw new UserException("User with email already exists");
        }

        // Save user details
        try {
            log.info("Registering User with email: {}", signupDto.getEmail());
            // Create user in IAM
            String iamId = keycloakService.createUser(signupDto);

            Users users = Users.builder().firstName(signupDto.getFirstName())
                .lastName(signupDto.getLastName()).iamId(iamId).birthDate(signupDto.getBirthDate())
                .createdOn(Instant.now()).email(signupDto.getEmail()).roleId(1).emailVerified(false)
                .isMessenger(signupDto.getIsMessenger()).deceased(false).isFirstLetterCreated(false)
                .isFirstVideoCreated(false).isFirstVideoCreated(false).hasWritten(false).build();
            usersRepository.save(users);

            if (signupDto.getIsMessenger()) {
                // To check possession of invitation token
                if (StringUtils.isEmpty(signupDto.getInvitationToken())) {
                    // usersRepository.delete(users);
                    keycloakService.deleteUser(iamId);
                    throw new UserException("User invitation token not found");
                }
                Optional<Messenger> messenger = messengerRepository.findByInvitationToken(signupDto.getInvitationToken());
                if (!messenger.isPresent()) {
                    keycloakService.deleteUser(iamId);
                    throw new UserException("Invitation token not found");
                }
                if (messenger.get().getEmail().equalsIgnoreCase(signupDto.getEmail())) {
                    keycloakService.verifyEmail(users.getEmail());
                    users.setEmailVerified(true);
                    return;
                }
            }

            String token = JWTUtils.generateVerificationToken(iamId);
            //TODO remove println as soon as mail sender is done
            //System.out.println("**** : " + token);

            EmailMessage emailMsg = getEmailMessagePojo(fromAddress, users.getEmail(),
                LFareWellConstants.SIGN_UP_SUBJECT, LFareWellConstants.SIGN_UP_TEMPLATE,
                users.getFirstName() + " " + users.getLastName(), token);

            emailService.sendEmail(emailMsg);
            // https://lastfarewells.vercel.app/signup/token
        } catch (Exception e) {
            // Compensation action: delete the user from auth service if profile
            // registration fails
            // userAuthRepository.deleteByEmail(signupDto.getEmail());
            log.error("User registration failed : {}", e.getMessage());
            throw new UserException("User registration failed");
        }

    }

    private EmailMessage getEmailMessagePojo(String from, String to, String subject,
        String templateName, String userName, String token) {
        Map<String, Object> props = new HashedMap<>();
        props.put(LFareWellConstants.USER_NAME, userName);
        props.put(LFareWellConstants.TOKEN, token);
        props.put(LFareWellConstants.SUBJECT, subject);
        return new EmailMessage(from, to, subject, templateName, props);
    }


    @Override
    public UserAccessTokenDto authenticateUser(LoginDto loginDto) {
        Users user = usersRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new UserException("User with email not found"));

        AccessTokenResponse accessTokenResponse = keycloakService.login(loginDto);

        Boolean isEmailVerified = (Boolean) JWTUtils.decodeJWT(accessTokenResponse.getToken())
            .getPayload().toJSONObject().get("email_verified");
        if (isEmailVerified == null || !isEmailVerified) {
            throw new UserAuthenticationException("User's email is not verified!!");
        }
        if (!user.getEmailVerified() && isEmailVerified) {
            user.setEmailVerified(true);
        }
        log.info("User successfully verified for login {}", loginDto.getEmail());
        // Update user's last login
        user.setLastLogin(Instant.now());
        usersRepository.save(user);
        return new UserAccessTokenDto(user.getId(), accessTokenResponse);
    }

    @Override
    public void forgotPassword(String email) {
        Users user = usersRepository.findByEmail(email).orElseThrow(() -> new UserException("User with email not found"));
        log.info("User {} requested for password reset", email);

        String token = JWTUtils.generateVerificationToken(user.getIamId());
        // TODO remove println as soon as mail sender is done
        System.out.println("**** : " + token);
        // Store token for validation

        EmailMessage emailMsg = getEmailMessagePojo(fromAddress, user.getEmail(),
            LFareWellConstants.RESET_PASSWORD_SUBJECT, LFareWellConstants.RESET_PASSWORD_TEMPLATE,
            user.getFirstName() + " " + user.getLastName(), token);

        emailService.sendEmail(emailMsg);
        // mailService.sendResetPassword(user.getIamId());
        // https://lastfarewells.vercel.app/reset-password/token
    }


    @Override
    public void resetUserPassword(PasswordResetDto passwordResetDto) {
        // Find PasswordResetToken by token
        Users user = usersRepository.findByEmail(passwordResetDto.getEmail())
            .orElseThrow(() -> new UserException("User with email not found"));
        // Validate token expiration
        String subject = JWTUtils.getEmailFromToken(passwordResetDto.getToken());
        Users subjectUser = usersRepository.findByIamId(subject)
            .orElseThrow(() -> new UserException("Invalid token for User"));
        if (!subjectUser.getEmail().equals(user.getEmail())) {
            throw new UserAuthenticationException("Invalid email for password reset request for provided token");
        }
        if (!JWTUtils.verifyToken(passwordResetDto.getToken())) {
            throw new UserAuthenticationException("Invalid or expired token");
        }
        try {
            keycloakService.updatePassword(user.getIamId(), passwordResetDto.getPassword());
        } catch (Exception e) {
            log.error("Password Reset failed : {}", e.getMessage());
            throw new UserException("Password Reset failed");
        }
    }

    @Override
    public void verifyEmail(VerifyEmailDto verifyEmailDto) {
        String subject = JWTUtils.getEmailFromToken(verifyEmailDto.getToken());
        Users subjectUser = usersRepository.findByIamId(subject)
            .orElseThrow(() -> new UserException("Invalid token for User"));
        try {
            keycloakService.verifyEmail(subjectUser.getEmail());
        } catch (Exception e) {
            log.error("Email Verification failed : {}", e.getMessage());
            throw new UserException("Email Verification failed");
        }
    }

    @Override
    public UserDetailsDto getUser() {
        String iamId = JWTUtils.getUserIdFromToken();
        Users user = usersRepository.findByIamId(iamId)
            .orElseThrow(() -> new UserException("User Not Found"));
        UserDetailsDto userDetailsDto = UserDetailsDto.builder().build();
        modelMapper.map(user, userDetailsDto);
        // TODO add user subscription details
        userDetailsDto.setSubscription(SubscriptionDto.builder()
                .subscriptionId(1L).name("Freemium")
                .feature(List.of(Feature.builder().id(1L).name("last_letters").build(), Feature.builder().id(2L).name("last_videos").build(),
                    Feature.builder().id(3L).name("last_audios").build(), Feature.builder().id(4L).name("messengers").build(),
                    Feature.builder().id(5L).name("memorial_page").build(), Feature.builder().id(6L).name("photos")
                        .build()))
            .plan(Plan.builder().id(1).photos(MediaData.builder().dataCountLimit(-1).uploadSizeLimit(5).build())
                .lastAudios(AudioSettings.builder().lengthLimit(3).dataCountLimit(3).uploadSizeLimit(25).build())
                .lastVideos(VideoSettings.builder().lengthLimit(2).dataCountLimit(3).uploadSizeLimit(50).build())
                .lastLetters(MediaData.builder().uploadSizeLimit(5).dataCountLimit(3).build()).build())
            .build());
        // add message count from DB
        LastMessageCount lastMessageCount = messagesRepository.findMessageCountByUserId(userDetailsDto.getId());
        userDetailsDto.setMessagesCount(MessageCountDto.builder().letters(Long.valueOf(lastMessageCount.getLetterCount()))
            .videos(Long.valueOf(lastMessageCount.getVideoCount())).audios(Long.valueOf(lastMessageCount.getAudioCount())).build());
        return userDetailsDto;
    }

    @Override
    public Users updateUser(Long id, UpdateUserDto updateUserDto) {
        Users user = usersRepository.findById(id)
            .orElseThrow(() -> new UserException("User not found"));

        modelMapper.map(updateUserDto, user);
        user.setUpdatedOn(Instant.now());
        return usersRepository.save(user);
    }

}
