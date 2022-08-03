package com.axilog.cov.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

	private String id;
	private String login;
	private String email;
	private String password;
	private String oldPassword;
	private String firstName;
	private String lastName;
	private boolean activated = false;
	private String langKey;

	private List<String> authorities;
	private String createdBy;
	private Date createdDate;
	private String lastModifiedBy;
	private Date lastModifiedDate;

}
