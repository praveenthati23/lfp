package com.lastfarewells.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(name = "country_id")
    private Long       countryId;
    @Column(name = "city")
    private String     city;
    @Column(name = "state")
    private String     state;
    @Column(name = "zip")
    private String     zip;
    @Column(name = "birth_country_id")
    private Long       birthCountryId;
    @Column(name = "birth_city")
    private String     birthCity;
    @Column(name = "birth_state")
    private String     birthState;
    @Column(name = "birth_zip")
    private String     birthZip;
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
    @Column(name = "birth_address")
    private String     birthAddress;
    @Column(name = "address")
    private String     address;
    @Column(name = "contact_number")
    private String     contactNumber;
    @Column(name = "secondary_email")
    private String     secondaryEmail;
    @Column(name = "created_on")
    private Instant    createdOn;
    @Column(name = "updated_on")
    private Instant    updatedOn;

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
