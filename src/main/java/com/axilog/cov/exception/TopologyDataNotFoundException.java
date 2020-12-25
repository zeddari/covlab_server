package com.axilog.cov.exception;

import com.axilog.cov.exception.base.NotFoundException;
import com.axilog.cov.exception.enums.ExceptionEnum;

public class TopologyDataNotFoundException extends NotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String message;

	public TopologyDataNotFoundException(ExceptionEnum exceptionEnum) {
		super(exceptionEnum);
		this.code = exceptionEnum.getCode();
		this.message = exceptionEnum.getMessage();
	}

}
