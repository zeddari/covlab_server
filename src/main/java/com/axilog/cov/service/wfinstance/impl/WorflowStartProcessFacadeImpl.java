package com.axilog.cov.service.wfinstance.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axilog.cov.constant.WorkflowProcessDefinitionsEnum;
import com.axilog.cov.constant.WorkflowVariables;
import com.axilog.cov.service.activity.api.WorflowManagementService;
import com.axilog.cov.service.wfinstance.api.WorflowStartProcessFacade;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WorflowStartProcessFacadeImpl implements WorflowStartProcessFacade {
	
	@Autowired
	WorflowManagementService worflowManagementService;
	
	
	@Override
	public void startNewNEWorkflow(String name) throws Exception {
		Map<String, Object> processVariables = new HashMap<>();
		processVariables.put(WorkflowVariables.BUSINESS_KEY, name);
		processVariables.put(WorkflowVariables.EQUIP_NAME, name);
		try {
			worflowManagementService.startProcessInstanceByKey(WorkflowProcessDefinitionsEnum.NEW_EQIUPMENT_DEFINITION_ID.getDefinitionId(), name, processVariables);
		} catch (Exception e) {
			log.error("CANCEL PROCESS DUPLICATION: A Simple Cancel Process for the name {} has aleready been started",name);
		}
		
	}
}