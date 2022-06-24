package com.axilog.cov.service.activity.impl;

import java.util.List;
import java.util.Map;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axilog.cov.service.activity.api.WorflowManagementService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WorkflowManagementServiceImpl implements WorflowManagementService {

	@Autowired
	protected RuntimeService runtimeService;

	@Autowired
	protected HistoryService historyService;

	public ProcessInstance startProcessInstanceByKey(final String processDefinitionId, final String businessKey,
			final Map<String, Object> variables) {
		List<ProcessInstance> processList = findProcessListByBusinessKey(processDefinitionId, businessKey);
		log.info("############################## Starting new Workflow : {} for applicationId {} ############################## ",processDefinitionId,businessKey);
		return runtimeService.startProcessInstanceByKey(processDefinitionId, businessKey, variables);
	}

	@Override
	public void cancelProcess(final String businessKey, String causeMessage, String callingProcess) {
			}
	
	private void deleteProcess(final String processDefinitionKey, final String businessKey, String causeMessage, String callingProcess) {
		if(!processDefinitionKey.equals(callingProcess)) {
			List<ProcessInstance> pList = findListByBusinessKey(processDefinitionKey, businessKey);
			if(pList!=null && !pList.isEmpty()) {
				pList.forEach( p -> {
					log.info("terminate process instance {} with business key {} and the processInstId {}",processDefinitionKey,businessKey, p.getId());
					runtimeService.deleteProcessInstance(p.getId(), causeMessage);
				});
				
			}
			else {
				log.warn("No process found to be deleted for the following criterias: processDefinitionKey= {}, businessKey= {}", processDefinitionKey, businessKey);
			}
			//try catch process not found, 
		}

	}
	
	
	@Override
	public ProcessInstance findByBusinessKey(final String processDefinitionKey, final String businessKey) {
		return runtimeService.createProcessInstanceQuery()//
				.processDefinitionKey(processDefinitionKey)//
				.processInstanceBusinessKey(businessKey)//
				.singleResult();
	}

	@Override
	public List<ProcessInstance> findListByBusinessKey(final String processDefinitionKey, final String businessKey) {
		return runtimeService.createProcessInstanceQuery()//
				.processDefinitionKey(processDefinitionKey)//
				.processInstanceBusinessKey(businessKey)//
				.list();
	}
	@Override
	public boolean hasNeverBeenProcessed(final String processDefinitionKey, final String businessKey) {
		return historyService.createHistoricProcessInstanceQuery().processDefinitionKey(processDefinitionKey)
				.processInstanceBusinessKey(businessKey).list().isEmpty()
				&& findByBusinessKey(processDefinitionKey, businessKey) == null;
	}

	@Override
	public Execution findByMessageEventSubscriptionName(final String messageEventName, final String businessKey) {
		return runtimeService.createExecutionQuery().messageEventSubscriptionName(messageEventName)
				.processInstanceBusinessKey(businessKey, true).singleResult();
	}
	// runtimeService.createExecutionQuery().processVariableValueEquals("sdf",
	// "sdf").signalEventSubscriptionName("").processInstanceBusinessKey(businessKey,
	// true).singleResult();

	@Override
	public void publishMessageEvent(final String messageEventName, final String executionId) {
		runtimeService.messageEventReceived(messageEventName, executionId);
	}

	@Override
	public void publishMessageEvent(final String messageEventName, final String executionId,
			Map<String, Object> variables) {
		runtimeService.messageEventReceived(messageEventName, executionId, variables);
	}

	@Override
	public void setVariable(final String processInstanceId, final String key, final Object value) {
		runtimeService.setVariable(processInstanceId, key, value);
	}

	@Override
	public Object getVariable(final String processInstanceId, final String key) {
		return runtimeService.getVariable(processInstanceId, key);
	}

	@Override
	public void notifyReceiveOrTriggerableTask(final String businessKey, final String receiveTaskId,
			final Map<String, Object> variables, Map<String, Object> transientVariables){
		Execution execution = runtimeService.createExecutionQuery().processInstanceBusinessKey(businessKey, true)
				.activityId(receiveTaskId).singleResult();
		if (execution == null) {
			
		}
		runtimeService.trigger(execution.getId(), variables, transientVariables);
	}
	
	private List<ProcessInstance> findProcessListByBusinessKey(final String processDefinitionKey, final String businessKey) {
		return runtimeService.createProcessInstanceQuery()//
				.processDefinitionKey(processDefinitionKey)//
				.processInstanceBusinessKey(businessKey)//
				.list();
	}
}
