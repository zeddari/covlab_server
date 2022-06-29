package com.axilog.cov.facade.api.workflow;

import com.axilog.cov.dto.command.workflow.StartQuotationRequestProcessCommand;
import com.axilog.cov.exception.process.DuplicatedProcessSameBKException;

public interface WorkflowStartProcessFacade {

	void startQuotationRequestProcess(StartQuotationRequestProcessCommand captureStartCommand) throws DuplicatedProcessSameBKException;

	
}
