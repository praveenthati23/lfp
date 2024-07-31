package com.lastfarewells.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "google_open_id")
    private String     googleOpenId;
    @Column(name = "iam_id")
    private String     iamId;
    @Column(name = "first_name")
    private String     firstName;
    @Column(name = "last_name")
    private String     lastName;
    @Column(name = "birth_date")
    private Date       birthDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GenderEnum gender;
    @Column(name = "photo_url")
    private String     photoUrl;
    @Column(name = "facebook_url")
    private String     facebookUrl;
    @Column(name = "x_url")
    private String     xUrl;
    @Column(name = "insta_url")
    private String     instaUrl;
    @Column(name = "tiktok_url")
    private String     tiktokUrl;
    @Column(name = "deceased")
    private Boolean    deceased;
    @Column(name = "death_date")
    private Date       deathDate;
    @Column(name = "is_first_letter_created")
    private Boolean    isFirstLetterCreated;
    @Column(name = "is_first_video_created")
    private Boolean    isFirstVideoCreated;
    @Column(name = "is_first_audio_created")
    private Boolean    isFirstAudioCreated;
    @Column(name = "has_written")
    private Boolean    hasWritten;

    @ManyToOne
    @JoinColumn(name = "address_id")
    @Cascade(CascadeType.ALL)
    //@OnDelete(action = OnDeleteAction.CASCADE)
    private Address address;

    @ManyToOne
    @JoinColumn(name = "birth_address_id")
    @Cascade(CascadeType.ALL)
    //@OnDelete(action = OnDeleteAction.CASCADE)
    private Address birthAddress;

    @Column(name = "contact_number")
    private String  contactNumber;
    @Column(name = "secondary_email")
    private String  secondaryEmail;
    @Column(name = "created_on")
    private Instant createdOn;
    @Column(name = "updated_on")
    private Instant updatedOn;

    @Column(name = "email")
    private String  email;
    @Column(name = "email_verified")
    private Boolean emailVerified;
    @Column(name = "status")
    private Boolean status;
    @Column(name = "is_trustor")
    private Boolean isTrustor;
    @Column(name = "last_login")
    private Instant lastLogin;
    @Column(name = "role_id")
    private Integer roleId;

}
