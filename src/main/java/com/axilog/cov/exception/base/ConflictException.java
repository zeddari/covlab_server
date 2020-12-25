package com.axilog.cov.exception.base;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.axilog.cov.exception.enums.ExceptionWorkflowEnum;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public ConflictException(String code, String libelle) {
		super(code, libelle);
	}

	public ConflictException(ExceptionWorkflowEnum exceptionEnum, Object... args) {
		super(exceptionEnum, args);
	}

}
