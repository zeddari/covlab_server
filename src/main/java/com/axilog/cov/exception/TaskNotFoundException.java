package com.axilog.cov.exception;

import com.axilog.cov.exception.base.NotFoundException;
import com.axilog.cov.exception.enums.ExceptionWorkflowEnum;

public class TaskNotFoundException extends NotFoundException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TaskNotFoundException(String taskId) {
		super(ExceptionWorkflowEnum.TASK_NOT_FOUND, taskId);
	}
}
