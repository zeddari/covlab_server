package com.axilog.cov.dto.mapper;

import org.flowable.engine.ManagementService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.job.api.DeadLetterJobQuery;
import org.flowable.job.api.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axilog.cov.dto.representation.DeadLetterJobRepresentation;

@Component
public class DeadLetterJobMapper {

	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private ManagementService managementService;

	public DeadLetterJobRepresentation map(Job job) {
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery() //
				.processInstanceId(job.getProcessInstanceId()) //
				.singleResult();
		Execution execution = runtimeService.createExecutionQuery().executionId(job.getExecutionId()).singleResult();

		return DeadLetterJobRepresentation.builder()//
				.id(job.getId()) //
				.executionId(job.getExecutionId()) //
				.businessKey(processInstance.getBusinessKey()).processInstanceId(job.getProcessInstanceId()) //
				.processDefinitionId(job.getProcessDefinitionId()) //
				.processDefinitionName(processInstance.getProcessDefinitionName()) //
				.processDefinitionVersion(processInstance.getProcessDefinitionVersion()) //
				.exceptionMessage(job.getExceptionMessage()) //
				.activityId(execution.getActivityId())//
				.dueDate(job.getDuedate())//
				.retries(job.getRetries())//
				.build();
	}

	public DeadLetterJobRepresentation toDeadLetterJobRepresentation(Execution execution) {
		final DeadLetterJobQuery jobQuery = managementService.createDeadLetterJobQuery();
		Job job = jobQuery.jobId(execution.getId()).singleResult();

		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery() //
				.processInstanceId(job.getProcessInstanceId()) //
				.singleResult();
		return DeadLetterJobRepresentation.builder()//
				.id(job.getId()) //
				.executionId(job.getExecutionId()) //
				.businessKey(processInstance.getBusinessKey()).processInstanceId(job.getProcessInstanceId()) //
				.processDefinitionId(job.getProcessDefinitionId()) //
				.processDefinitionName(processInstance.getProcessDefinitionName()) //
				.processDefinitionVersion(processInstance.getProcessDefinitionVersion()) //
				.exceptionMessage(job.getExceptionMessage()) //
				.activityId(job.getElementId())//
				.dueDate(job.getDuedate())//
				.retries(job.getRetries())//
				.build();
	}
}
