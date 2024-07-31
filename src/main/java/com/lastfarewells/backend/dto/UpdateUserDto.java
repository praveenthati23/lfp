package com.lastfarewells.backend.dto;

import com.lastfarewells.backend.entity.GenderEnum;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {

    private String     googleOpenId;
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

    private AddressDto address;
    private AddressDto birthAddress;

    private String contactNumber;
    private String secondaryEmail;

}
