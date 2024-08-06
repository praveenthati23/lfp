package com.lastfarewells.backend.dto;

import com.lastfarewells.backend.entity.Address;
import com.lastfarewells.backend.entity.GenderEnum;
import java.time.Instant;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailsDto {

    private Long id;

    private String     googleOpenId;
    private String     iamId;
    private String     firstName;
    private String     lastName;
    private Date       birthDate;
    private GenderEnum gender;
    private String     photoUrl;
    private String     facebookUrl;
    private String     xUrl;
    private String     instaUrl;
    private String     tiktokUrl;
    private Boolean    deceased;
    private Date       deathDate;
    private Boolean    isFirstLetterCreated;
    private Boolean    isFirstVideoCreated;
    private Boolean    isFirstAudioCreated;
    private Boolean    hasWritten;
    private Address    address;
    private Address    birthAddress;
    private String     contactNumber;
    private String     secondaryEmail;
    private Instant    createdOn;
    private Instant    updatedOn;
    private String     email;
    private Boolean    emailVerified;
    private Boolean    status;
    private Boolean    isMessenger;
    private Instant    lastLogin;
    private Integer    roleId;

    private SubscriptionDto subscription;
    private MessageCountDto messagesCount;

}
