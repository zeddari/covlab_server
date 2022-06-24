package com.axilog.cov.service.activity.api;

import java.util.List;
import java.util.Map;

import com.axilog.cov.dto.TaskDto;
import com.axilog.cov.dto.UserTasksListQuery;
import com.axilog.cov.dto.base.PageRepresentation;
import com.axilog.cov.exception.NextTaskNotAvailableException;
import com.axilog.cov.exception.NextTaskNotFoundException;
import com.axilog.cov.exception.RequestNotFoundException;

public interface TaskApiService {

	public TaskDto nextTask(String userId, String group) throws NextTaskNotFoundException;
	public void claimTask(String taskId, String userId);
	public void unclaimTask(String taskId, String userId);
	public void completeTask(String taskId, String userId, Map<String, Object> variables, List<String> groups) throws RequestNotFoundException;
	public long count(String groupId);
	public TaskDto nextTaskByApplicatioId(String userId, String group, String applicationId) throws NextTaskNotAvailableException;
	public PageRepresentation<TaskDto> getUserTaskPageByBusinessKey(String userId, UserTasksListQuery query);
	public List<TaskDto> getUserTaskListByBusinessKey(String userId, UserTasksListQuery query);

}
