package com.axilog.cov.exception.process;

import com.axilog.cov.exception.base.ConflictException;
import com.axilog.cov.exception.enums.ExceptionWorkflowEnum;

public class DuplicatedProcessSameBKException extends ConflictException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicatedProcessSameBKException(String definitionId, String businessKey) {
		super(ExceptionWorkflowEnum.PROCESS_WITH_SAME_DEFINITION_ID_AND_BUSINESS_KEY_EXIST, definitionId, businessKey);
	}

}
