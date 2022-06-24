package com.axilog.cov.dto.builder;

import org.flowable.task.api.TaskQuery;
import org.springframework.util.StringUtils;

import com.axilog.cov.constant.WorkflowVariables;
import com.axilog.cov.dto.UserTasksListQuery;

public class TaskQueryBuilder {

	private static final String ASSIGNED_TO_ME = "ASSIGNED_TO_ME";
	private static final String NOT_ASSIGNED = "UNASSIGNED";
	private static final String SUSPEND = "SUSPEND";
	private static final String UNSUSPEND = "UNSUSPEND";
	private static final String ALL_TASKS = "ALL";

	private TaskQuery taskQuery;
	private boolean userAssignedInQuery;
	private UserTasksListQuery query;

	private TaskQueryBuilder(TaskQuery tq, UserTasksListQuery query) {
		this.taskQuery = tq;
		this.query = query;
		userAssignedInQuery = (ASSIGNED_TO_ME.equalsIgnoreCase(query.getAssigned())) ? true : false;
		initTaskQuery();
	}

	private void initTaskQuery() {
		taskQuery.includeProcessVariables();
	}

	public TaskQueryBuilder buildSuspended() {
		if (SUSPEND.equalsIgnoreCase(query.getSuspend())) {
			taskQuery.suspended();
			return this;
		}
		if (UNSUSPEND.equalsIgnoreCase(query.getSuspend())) {
			taskQuery.active();
			return this;
		}
		return this;
	}

	public TaskQueryBuilder buildCandidateGroup() {
		if (!StringUtils.isEmpty(query.getGroup()) && !userAssignedInQuery) {
			taskQuery.taskCandidateGroup(query.getGroup());
		}
		return this;
	}

	public TaskQueryBuilder buildAssignment(String userId) {
		if (NOT_ASSIGNED.equalsIgnoreCase(query.getAssigned())) {
			taskQuery.taskUnassigned();
			return this;
		}
		if (ASSIGNED_TO_ME.equalsIgnoreCase(query.getAssigned())) {
			taskQuery.taskAssignee(userId);
			return this;
		}
		if (ALL_TASKS.equalsIgnoreCase(query.getAssigned())) {
			taskQuery.ignoreAssigneeValue();
			return this;
		}
		return this;
	}

	public void buildProcessVariables() {
		if (!StringUtils.isEmpty(query.getContext())) {
			taskQuery.processVariableValueEquals(WorkflowVariables.EQUIPMENT_CONTEXT, query.getContext());
		}
		if (!StringUtils.isEmpty(query.getBusinessKey())) {
			taskQuery.processInstanceBusinessKey(query.getBusinessKey());// busnisesKEy
		}
		if (!StringUtils.isEmpty(query.getEquipName())) {
			taskQuery.processVariableValueEquals(WorkflowVariables.EQUIP_NAME, query.getEquipName());
		}
	}

	public void buildSorting() {
		// Test on Sort input filter
		if (!StringUtils.isEmpty(query.getSort()) && query.getSort().equalsIgnoreCase("DESC")) {
			taskQuery.orderByTaskCreateTime().desc();
		}else if (!StringUtils.isEmpty(query.getSort()) && query.getSort().equalsIgnoreCase("ASC")) {
			taskQuery.orderByTaskCreateTime().asc();
		}else {
			taskQuery.orderByTaskCreateTime().asc();
		}
	}
	public void buildTaskDefinitionKey() {
		if (!StringUtils.isEmpty(query.getTaskDefinitionKey())) {
			taskQuery.taskDefinitionKey(query.getTaskDefinitionKey());
		}
	}
	public TaskQuery getTaskQuery() {
		return taskQuery;
	}

	public static class Builder {

		private TaskQueryBuilder tb;

		public Builder(TaskQuery tq, UserTasksListQuery query) {
			tb = new TaskQueryBuilder(tq, query);
		}

		public Builder suspendCriteria() {
			tb = tb.buildSuspended();
			return this;
		}

		public Builder candidateGroup() {
			tb = tb.buildCandidateGroup();
			return this;
		}

		public Builder assignment(String userId) {
			tb.buildAssignment(userId);
			return this;
		}

		public Builder processVariables() {
			tb.buildProcessVariables();
			return this;
		}

		public Builder processTaskDefinitionKey() {
			tb.buildTaskDefinitionKey();
			return this;
		}
		
		public Builder sorting() {
			tb.buildSorting();
			return this;
		}

		public TaskQueryBuilder build() {
			return tb;
		}
	}
}
