package com.axilog.cov.exception.enums;

public enum ExceptionEnum {

	

	BAD_REQUEST("ERR_CODE_01", "BAD REQUEST"),
	MAIL_NOT_UNIQUE("ERR_CODE_03", "Mail entered is not unique thalla"),
	NOT_FOUND("ERR_CODE_02", "MW Link not found");
	
	ExceptionEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	private String code;
	private String message;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
