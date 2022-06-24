package com.axilog.cov.exception;

import com.axilog.cov.exception.base.NotFoundException;
import com.axilog.cov.exception.enums.ExceptionWorkflowEnum;

public class DeadLetterJobNotFoundException extends NotFoundException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeadLetterJobNotFoundException(String jobId) {
		super(ExceptionWorkflowEnum.DEAD_LETTER_JOB_NOT_FOUND, jobId);
	}
}
