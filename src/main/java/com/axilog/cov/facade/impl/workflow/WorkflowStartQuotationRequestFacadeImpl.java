/**
 *
 */
package com.axilog.cov.facade.impl.workflow;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axilog.cov.constant.WorkflowProcessDefinitionsEnum;
import com.axilog.cov.constant.WorkflowVariables;
import com.axilog.cov.domain.RequestQuotation;
import com.axilog.cov.dto.command.workflow.StartQuotationRequestProcessCommand;
import com.axilog.cov.exception.process.DuplicatedProcessSameBKException;
import com.axilog.cov.facade.api.workflow.WorkflowStartProcessFacade;
import com.axilog.cov.repository.RequestQuotationRepository;
import com.axilog.cov.service.activity.api.WorflowManagementService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WorkflowStartQuotationRequestFacadeImpl implements WorkflowStartProcessFacade {

	@Autowired
	private WorflowManagementService workflowManagementService;
	@Autowired
	RequestQuotationRepository requestQuotationRepository;
	@Override
	public void startQuotationRequestProcess(StartQuotationRequestProcessCommand startQuotationRequestProcessCommand)
			throws DuplicatedProcessSameBKException {
		Map<String, Object> processVariables = new HashMap<>();
		String applicationId = startQuotationRequestProcessCommand.getApplicationId();
		processVariables.put(WorkflowVariables.APPLICATION_ID, applicationId);
		processVariables.put("emailDestination", startQuotationRequestProcessCommand.getEmailDestination());
        processVariables.put("requestedProductCode", startQuotationRequestProcessCommand.getRequestedProductCode());
        processVariables.put("serviceType", startQuotationRequestProcessCommand.getServiceType());
        processVariables.put("requestQuotationId", startQuotationRequestProcessCommand.getRequestQuotationId());
        processVariables.put("serviceLocationLong", startQuotationRequestProcessCommand.getServiceLocationLong());
        processVariables.put("serviceLocationLati", startQuotationRequestProcessCommand.getServiceLocationLati());
        processVariables.put("productAmount", startQuotationRequestProcessCommand.getProductAmount());


		workflowManagementService.startProcessInstanceByKey(
				WorkflowProcessDefinitionsEnum.QUOTATION_REQUEST_PROCESS_DEFINITION_ID.getDefinitionId(), applicationId,
				processVariables);
		//Add Amount quotation to quotation 
		RequestQuotation requestQuotation=	requestQuotationRepository.findByRequestQuotationId(startQuotationRequestProcessCommand.getRequestQuotationId());
		requestQuotation.setQuotationAmount(Double.valueOf(startQuotationRequestProcessCommand.getRequestQuotationId()));

		requestQuotationRepository.save(requestQuotation);

	}




}
