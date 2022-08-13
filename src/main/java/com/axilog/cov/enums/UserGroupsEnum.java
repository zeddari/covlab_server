package com.axilog.cov.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserGroupsEnum {

	DRIVERS(1l, "ROLE_DRIVER"),
	SUPERVISOR(2l, "ROLE_SUPERVISOR"),
	INTEGRATORS(2l, "ROLE_INTEGRATORION"),
	ADMINISTRATORS(4l, "ROLE_ADMIN"),
	APPROVERS(5l, "APPROVERS");

	private Long code;
	private String label;
}
