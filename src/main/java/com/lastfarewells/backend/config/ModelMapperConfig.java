package com.lastfarewells.backend.config;

import com.lastfarewells.backend.dto.AddressDto;
import com.lastfarewells.backend.dto.UpdateUserDto;
import com.lastfarewells.backend.entity.Address;
import com.lastfarewells.backend.entity.Users;
import org.modelmapper.Condition;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        // Custom mapping for UserDto to User
        modelMapper.addMappings(new PropertyMap<UpdateUserDto, Users>() {
            @Override
            protected void configure() {
                Condition<?, ?> notNull = Conditions.isNotNull();
                when(notNull).map().setGoogleOpenId(source.getGoogleOpenId());
                when(notNull).map().setFirstName(source.getFirstName());
                when(notNull).map().setLastName(source.getLastName());
                when(notNull).map().setBirthDate(source.getBirthDate());
                when(notNull).map().setGender(source.getGender());
                when(notNull).map().setPhotoUrl(source.getPhotoUrl());
                when(notNull).map().setFacebookUrl(source.getFacebookUrl());
                when(notNull).map().setXUrl(source.getXUrl());
                when(notNull).map().setInstaUrl(source.getInstaUrl());
                when(notNull).map().setTiktokUrl(source.getTiktokUrl());
                when(notNull).map().setDeceased(source.getDeceased());
                when(notNull).map().setDeathDate(source.getDeathDate());
                when(notNull).map().setSecondaryEmail(source.getSecondaryEmail());
                when(notNull).map().setContactNumber(source.getContactNumber());
                //when(notNull).map(src -> source.getAddress(), (dest, v) -> dest.setAddress(modelMapper.map(v, Address.class)));
                /*if (source.getAddress() != null) {
                    when(notNull).map().setAddress(modelMapper.map(source.getAddress(), Address.class));
                }
                if (source.getBirthAddress() != null) {
                    when(notNull).map().setBirthAddress(modelMapper.map(source.getBirthAddress(), Address.class));
                }*/
                // when(notNull).map().setAddress(source.getAddress());
                //when(notNull).map().setContactNumber(source.getContactNumber());
                when(notNull).map().getAddress().setCity(source.getAddress().getCity());
                when(notNull).map().getAddress().setAddress(source.getAddress().getAddress());
                when(notNull).map().getAddress().setCountryId(source.getAddress().getCountryId());
                when(notNull).map().getAddress().setState(source.getAddress().getState());
                when(notNull).map().getAddress().setZip(source.getAddress().getZip());

                skip().setId(null);
                skip().setEmail(null);
                skip().getAddress().setId(null);
            }
        });

        // Custom mapping for AddressDto to Address
        modelMapper.addMappings(new PropertyMap<AddressDto, Address>() {
            @Override
            protected void configure() {
                Condition<?, ?> notNull = Conditions.isNotNull();

                when(notNull).map().setCity(source.getCity());
                when(notNull).map().setState(source.getState());
                when(notNull).map().setAddress(source.getAddress());
                when(notNull).map().setCountryId(source.getCountryId());
                when(notNull).map().setZip(source.getZip());
                skip().setId(null);
            }
        });

        return modelMapper;
    }

}
