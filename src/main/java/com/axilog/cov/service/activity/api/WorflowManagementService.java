package com.axilog.cov.service.activity.api;

import java.util.List;
import java.util.Map;

import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;



public interface WorflowManagementService {

	/**
	 * Start a flowable process by :
	 * 
	 * @param processDefinitionId
	 * @param businessKey
	 * @param variables
	 * @throws DuplicatedProcessSameBKException 
	 */
	ProcessInstance startProcessInstanceByKey(final String processDefinitionId, final String businessKey,
			final Map<String, Object> variables);

	/**
	 * Find a process instance by bisness key
	 *
	 * @param processDefinitionId
	 * @param businessKey
	 * @return
	 */
	ProcessInstance findByBusinessKey(final String processDefinitionId, final String businessKey);

	/**
	 * Find a Boundary message event Execution by :
	 * 
	 * @param messageEventName
	 * @param businessKey
	 * @return
	 */
	Execution findByMessageEventSubscriptionName(final String messageEventName, final String businessKey);

	/**
	 * Notify the Boundary message event that the message has arrived
	 * 
	 * @param messageEventName
	 * @param executionId
	 */
	void publishMessageEvent(final String messageEventName, final String executionId);

	/**
	 * Notify the Boundary message event that the message has arrived and passes
	 * parameters
	 * 
	 * @param messageEventName
	 * @param executionId
	 */
	void publishMessageEvent(String messageEventName, String executionId, Map<String, Object> variables);

	/**
	 * Notifies a receive task that and event has arrived
	 * 
	 * @param businessKey
	 * @param receiveTaskId
	 * @param processVariables
	 * @throws NoProcessWaitingForReceiveTaskEvent
	 */

	void notifyReceiveOrTriggerableTask(String businessKey, String receiveTaskId, Map<String, Object> variables,
			Map<String, Object> transientVariables);

	/**
	 * Set variable to flowable process by :
	 * 
	 * @param processInstanceId
	 * @param key
	 * @param value
	 * @return
	 */
	void setVariable(final String processInstanceId, final String key, final Object value);

	/**
	 * Get variable from flowable process by key
	 * 
	 * @param processInstanceId
	 * @param key
	 * @return
	 */
	Object getVariable(final String processInstanceId, final String key);

	/**
	 * Checks if a process instance has ever been executed before for the given
	 * processDefinitionKey with the given businessKey.
	 * 
	 * @param processDefinitionKey
	 * @param businessKey
	 * @return
	 */
	boolean hasNeverBeenProcessed(String processDefinitionKey, String businessKey);

	/**
	 * @param businessKey
	 * @param causeMessage
	 * @param callingProcess
	 */
	void cancelProcess(String businessKey, String causeMessage, String callingProcess);
	
	/**
	 * @param processDefinitionKey
	 * @param businessKey
	 * @return
	 */
	List<ProcessInstance> findListByBusinessKey(String processDefinitionKey, String businessKey);

}
