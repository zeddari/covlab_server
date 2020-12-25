package com.axilog.cov.exception.base;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.axilog.cov.exception.enums.ExceptionWorkflowEnum;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)

public class BadRequestException extends BusinessException {
	private static final long serialVersionUID = -6958128182491079251L;

	public BadRequestException(String code, String libelle) {
		super(code, libelle);
	}

	public BadRequestException(ExceptionWorkflowEnum exceptionNum) {
		super(exceptionNum.getCode(), exceptionNum.getLibelle());
	}

	public BadRequestException(ExceptionWorkflowEnum exEnum, Object... args) {
		super(exEnum.getCode(), String.format(exEnum.getLibelle(), args));
	}
}