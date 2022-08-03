package com.axilog.cov.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonString {

	public static final String SUCCESS = "Success";


	private String message;

	public static JsonString success() {
	 	return new JsonString(SUCCESS);
	}
}
