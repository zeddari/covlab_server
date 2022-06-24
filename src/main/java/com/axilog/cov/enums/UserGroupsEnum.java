package com.axilog.cov.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserGroupsEnum {

	ACQUISITORS(1l, "ROLE_ACQUISITION"),
	CONSTRUCTORS(2l, "ROLE_CONSTRUCTION"),
	INTEGRATORS(2l, "ROLE_INTEGRATORION"),
	SUPERVISORS(3l, "ROLE_SUPERVISORS"),
	ADMINISTRATORS(4l, "ROLE_ADMIN"),
	APPROVERS(5l, "APPROVERS");

	private Long code;
	private String label;
}
