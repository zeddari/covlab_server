package com.axilog.cov.service.activity.listeners;

import java.text.ParseException;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.stereotype.Component;

import com.axilog.cov.constant.WorkflowVariables;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AssignToDriverTaskHelper {

	public void assignToDriver(DelegateTask task, DelegateExecution execution) throws ParseException {
		log.info("assign task to driver {}");
		
	    String userId = (String) execution.getVariable(WorkflowVariables.USER_ID);
		task.setAssignee(userId);

	}

}
