package com.axilog.cov.service.impl;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.flowable.common.engine.api.query.QueryProperty;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.service.impl.TaskQueryProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.axilog.cov.aop.logging.annotation.ExcludeLog;
import com.axilog.cov.dto.TaskDto;
import com.axilog.cov.dto.UserTasksListQuery;
import com.axilog.cov.dto.base.PageRepresentation;
import com.axilog.cov.dto.builder.TaskQueryBuilder;
import com.axilog.cov.dto.representation.DefaultPageRepresentation;
import com.axilog.cov.exception.NextTaskNotAvailableException;
import com.axilog.cov.exception.NextTaskNotFoundException;
import com.axilog.cov.exception.RequestNotFoundException;
import com.axilog.cov.service.SlaService;
import com.axilog.cov.service.TaskService;
import com.axilog.cov.util.paging.RangeCriteria;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService{

	@Autowired
	private org.flowable.engine.TaskService taskService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private SlaService slaService;

	@Autowired
	private IdentityService identityService;

	/**
	 * @param userId
	 * @param group
	 * @return
	 * @throws NextTaskNotFoundException
	 */
	public TaskDto nextTask(String userId, String group) throws NextTaskNotFoundException {
		log.info("Next task, group = {}, userId {}", group, userId);
		log.debug("taskService.createTaskQuery() start for userId {}, time start {}", userId,
				System.currentTimeMillis());
		QueryProperty byPriorityAndCreationTime = TaskQueryProperty.CREATE_TIME;
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).includeProcessVariables()
				.orderBy(byPriorityAndCreationTime).orderByTaskPriority().asc().orderByTaskCreateTime().desc().list();
		if (tasks == null || tasks.isEmpty()) {
			tasks = taskService.createTaskQuery().taskCandidateGroup(group).includeProcessVariables()
					.orderBy(byPriorityAndCreationTime).orderByTaskPriority().asc().orderByTaskCreateTime().desc()
					.list();
		}
		log.debug("taskService.createTaskQuery() end for userId {}, time start {}", userId, System.currentTimeMillis());
		if (isNotEmpty(tasks)) {
			log.debug("claimTask start for userId {}, time start {}", userId, System.currentTimeMillis());
			Task next = tasks.iterator().next();
			claimTask(next.getId(), userId);
			log.debug("claimTask end for userId {}, time start {}", userId, System.currentTimeMillis());
			return TaskMapper.mapTo(next);
		}

		throw new NextTaskNotFoundException();
	}

	/**
	 * @param taskId
	 * @param userId
	 */
	public void claimTask(String taskId, String userId) {
		try {
			log.info("Claim task, taskId = {}, userId = {}", taskId, userId);

			identityService.setAuthenticatedUserId(userId);

			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
			String applicationId = processInstance.getBusinessKey();
			slaService.startSla(applicationId, "ADJUDICATION_USER");

			taskService.claim(taskId, userId);
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
	}

	/**
	 * @param taskId
	 * @param userId
	 */
	public void unclaimTask(String taskId, String userId) {
		try {
			log.info("UnClaim Task, taskId = {}, userId = {}", taskId, userId);

			identityService.setAuthenticatedUserId(userId);
			taskService.unclaim(taskId);
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
	}

	/**
	 * @param taskId
	 * @param userId
	 * @param variables
	 * @throws RequestNotFoundException 
	 */
	@ExcludeLog
	public void completeTask(String taskId, String userId, Map<String, Object> variables, List<String> groups) throws RequestNotFoundException {
		try {
			log.debug("Complete task, taskId = {}, userId = {}", taskId, userId);
			identityService.setAuthenticatedUserId(userId);

			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
			String applicationId = processInstance.getBusinessKey();
			taskService.complete(taskId, variables);
			slaService.endSla(applicationId, userId);
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
	}

	/**
	 * @param groupId
	 * @return
	 */
	public long count(String groupId) {
		return taskService.createTaskQuery().taskCandidateGroup(groupId).count();
	}

	/**
	 * @param userId
	 * @param group
	 * @param applicationId
	 * @return
	 * @throws NextTaskNotAvailableException
	 */
	public TaskDto nextTaskByApplicatioId(String userId, String group, String applicationId)
			throws NextTaskNotAvailableException {
		log.info("Next task,applicationId = {},  group = {}, userId {}", applicationId, group, userId);
		log.debug("taskService.createTaskQuery() start for userId {}, time start {}", userId,
				System.currentTimeMillis());
		QueryProperty byPriorityAndCreationTime = TaskQueryProperty.CREATE_TIME;
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).processInstanceBusinessKey(applicationId)
				.includeProcessVariables().orderBy(byPriorityAndCreationTime).orderByTaskPriority().asc()
				.orderByTaskCreateTime().desc().list();
		if (tasks == null || tasks.isEmpty()) {
			tasks = taskService.createTaskQuery().taskCandidateGroup(group).processInstanceBusinessKey(applicationId)
					.includeProcessVariables().orderBy(byPriorityAndCreationTime).orderByTaskPriority().asc()
					.orderByTaskCreateTime().desc().list();
		}
		log.debug("taskService.createTaskQuery() end for userId {}, time start {}", userId, System.currentTimeMillis());
		if (isNotEmpty(tasks)) {
			log.debug("claimTask start for userId {}, time start {}", userId, System.currentTimeMillis());
			Task next = tasks.get(0);
			claimTask(next.getId(), userId);
			log.debug("claimTask end for userId {}, time start {}", userId, System.currentTimeMillis());
			return TaskMapper.mapTo(next);
		}
		throw new NextTaskNotAvailableException();
	}

	/**
	 * @param userId
	 * @param query
	 * @return
	 */
	public PageRepresentation<TaskDto> getUserTaskPageByBusinessKey(String userId, UserTasksListQuery query) {
		log.info("Get user tasks page,group = {}, userId {}", query.getGroup(), userId);

		TaskQuery tq = new TaskQueryBuilder.Builder(taskService.createTaskQuery(), query).assignment(userId)
				.candidateGroup().processVariables().suspendCriteria().sorting().processTaskDefinitionKey().build().getTaskQuery();

		RangeCriteria rangeCriteria = RangeCriteria.fromPageCriteria(query.getPage(), query.getSize());
		return new DefaultPageRepresentation<>(
				TaskMapper.mapTo(tq.listPage(rangeCriteria.offset(), rangeCriteria.limit())), tq.count());

	}
	
	
	public List<TaskDto> getUserTaskListByBusinessKey(String userId, UserTasksListQuery query) {
		log.info("Get user tasks list,group = {}, userId {}", query.getGroup(), userId);
		TaskQuery tq = new TaskQueryBuilder.Builder(taskService.createTaskQuery(), query).assignment(userId)
				.candidateGroup().processVariables().suspendCriteria().sorting().processTaskDefinitionKey().build().getTaskQuery();
		return TaskMapper.mapTo(tq.list());
	}

	private static class TaskMapper {

		private TaskMapper() {

		}

		/**
		 * @param tasksDto
		 * @param tasks
		 */
		public static List<TaskDto> mapTo(List<Task> tasks) {

			if (!CollectionUtils.isEmpty(tasks)) {
				return tasks.stream().map(TaskMapper::mapTo).collect(Collectors.toList());
			}
			return Collections.emptyList();
		}

		public static TaskDto mapTo(Task task) {
			return TaskDto.builder().id(1L).taskId(task.getId()).priority(task.getPriority()).dueDate(task.getDueDate())
					.processInstanceId(task.getProcessInstanceId()).assignee(task.getAssignee())
					.processVariables(task.getProcessVariables())
					.taskName(task.getName())
					.requestId((String) task.getProcessVariables().get("requestId")).build();
		}
	}

}
