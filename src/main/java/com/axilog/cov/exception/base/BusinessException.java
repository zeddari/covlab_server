package com.axilog.cov.exception.base;

import com.axilog.cov.exception.enums.ExceptionWorkflowEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BusinessException extends Exception {

	private final String code;
	private final String message;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusinessException(String code, String message) {
		super();
		this.code = code;
		this.message = message;

	}

	public BusinessException(ExceptionWorkflowEnum exceptionEnum, Object... args) {
		super();
		this.code = exceptionEnum.getCode();
		this.message = String.format(exceptionEnum.getLibelle(), args);
	}

}
