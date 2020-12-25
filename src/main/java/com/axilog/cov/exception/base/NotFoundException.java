package com.axilog.cov.exception.base;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.axilog.cov.exception.enums.ExceptionEnum;
import com.axilog.cov.exception.enums.ExceptionWorkflowEnum;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends BusinessException {
	private static final long serialVersionUID = 1L;

	public NotFoundException(String code, String libelle) {
		super(code, libelle);
	}

	public NotFoundException(ExceptionWorkflowEnum exEnum, Object... args) {
		super(exEnum.getCode(), String.format(exEnum.getLibelle(), args));
	}
	
	public NotFoundException(ExceptionEnum exEnum, Object... args) {
		super(exEnum.getCode(), String.format(exEnum.getMessage(), args));
	}
}
