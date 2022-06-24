package com.axilog.cov.service.activity.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.NativeExecutionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axilog.cov.dto.builder.DeadLetterQueryBuilder;
import com.axilog.cov.dto.mapper.DeadLetterJobMapper;
import com.axilog.cov.dto.representation.DeadLetterJobRepresentation;
import com.axilog.cov.util.paging.SortCriteria;

@Component
public class DeasLetterJobQuerySearchFactory {

	@Autowired
	private DeadLetterJobMapper deadLetterJobMapper;

	@Autowired
	private RuntimeService runtimeService;

	public List<DeadLetterJobRepresentation> searchDeadJobs(final String businessKey, final String exceptionMessage,
			final String processDefinitionName, final String dueDate, final int offset, final int limit,
			SortCriteria sortCriteria) {
		final List<Execution> executions = DeadLetterQueryBuilder.create(runtimeService) //
				.where(businessKey, exceptionMessage, processDefinitionName, dueDate) //
				.sortBy(sortCriteria) //
				.query() //
				.listPage(offset, limit);
		if (executions != null) {

			return executions.stream().map(deadLetterJobMapper::toDeadLetterJobRepresentation)
					.collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	public long countSearchDeadJobs(final String businessKey, final String exceptionMessage,
			final String processDefinitionName, final String dueDate) {
		final NativeExecutionQuery executions = DeadLetterQueryBuilder.count(runtimeService) //
				.where(businessKey, exceptionMessage, processDefinitionName, dueDate) //
				.query();
		if (executions != null) {
			return executions.count();
		}
		return 0;
	}
}
