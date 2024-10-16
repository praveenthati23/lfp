package com.lastfarewells.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RelationShipEnum {
	CHILD("Child"), FRIEND("Friend"), PARENT("Parent"), PARTNER_SPOUSE("Partner/Spouse"), SIBLING("Sibling"),
	OTHER("Other");

	private final String value;
}
