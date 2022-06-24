package com.axilog.cov.exception;

import com.axilog.cov.exception.base.NotFoundException;
import com.axilog.cov.exception.enums.ExceptionWorkflowEnum;

public class RequestNotFoundException extends NotFoundException {

	private static final long serialVersionUID = 1L;

	public RequestNotFoundException(String applicationId) {
		super(ExceptionWorkflowEnum.REQUEST_NOT_FOUND, applicationId);
	}
}
