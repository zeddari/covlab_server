package com.axilog.cov.exception;

import com.axilog.cov.exception.base.NotFoundException;
import com.axilog.cov.exception.enums.ExceptionWorkflowEnum;

public class NextTaskNotAvailableException extends NotFoundException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public NextTaskNotAvailableException() {
		super(ExceptionWorkflowEnum.NEXT_TASK_NOT_AVAILABLE);
	}
}
