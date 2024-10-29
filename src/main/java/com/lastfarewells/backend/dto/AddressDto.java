package com.lastfarewells.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

	private Long id;

	private String address;

	private Long countryId;

	private String city;

	private String state;

	private String zip;

}
