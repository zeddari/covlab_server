package com.axilog.cov.service.activity.impl;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;

import org.flowable.engine.ManagementService;
import org.flowable.engine.RuntimeService;
import org.flowable.job.api.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.axilog.cov.constant.WorkflowVariables;
import com.axilog.cov.dto.base.PageRepresentation;
import com.axilog.cov.dto.query.SearchDeadLetterJobsQuery;
import com.axilog.cov.dto.representation.DeadLetterJobRepresentation;
import com.axilog.cov.dto.representation.DefaultPageRepresentation;
import com.axilog.cov.exception.DeadLetterJobNotFoundException;
import com.axilog.cov.service.activity.api.DeadLettersService;
import com.axilog.cov.util.paging.RangeCriteria;
import com.axilog.cov.util.paging.SortCriteria;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DeadLettersServiceImpl implements DeadLettersService {

	@Value("${jobs.deadletters.resume.retries:3}")
	private int deadLettersResumeRetries;

	@Autowired
	private ManagementService managementService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private DeasLetterJobQuerySearchFactory deasLetterJobQuerySearchFactory;

	@Override
	public void retry(String jobId) throws DeadLetterJobNotFoundException {
		retry(findById(jobId));
	}

	@Override
	public void retryAll() {
		log.info("Retry all jobs ");
		managementService.createDeadLetterJobQuery().list().stream()
				.forEach(this :: retry);
	}

	@Override
	public void delete(String jobId) throws DeadLetterJobNotFoundException {
		log.info("Delete job " + jobId);
		Job jobToDelete = findById(jobId);
		managementService.deleteDeadLetterJob(jobToDelete.getId());
	}

	@Override
	public Job findById(String jobId) throws DeadLetterJobNotFoundException {
		List<Job> listResult = managementService.createDeadLetterJobQuery().jobId(jobId).list();
		if (isEmpty(listResult)) {
			throw new DeadLetterJobNotFoundException(jobId);
		}

		return listResult.iterator().next();
	}

	@Override
	public void deleteAll() {
		log.info("Delete all jobs ");
		managementService.createDeadLetterJobQuery().list().stream()//
				.map(Job::getId)//
				.forEach(managementService::deleteDeadLetterJob);
	}

	@Override
	public PageRepresentation<DeadLetterJobRepresentation> search(SearchDeadLetterJobsQuery searchQuery) {
		log.debug("find page {}", searchQuery);

		if (searchQuery != null) {

			String businessKey = trimToEmptyWithLike(searchQuery.getBusinessKey());
			String exceptionMessage = trimToEmptyWithLike(searchQuery.getExceptionMessage());
			String processDefinitionName = trimToEmptyWithLike(searchQuery.getProcessDefinitionName());
			String dueDate = trimToEmptyWithLike(searchQuery.getDueDate());

			RangeCriteria rangeCriteria = RangeCriteria.fromPageCriteria(searchQuery.getPage(), searchQuery.getSize());

			long count = deasLetterJobQuerySearchFactory.countSearchDeadJobs(businessKey, exceptionMessage,
					processDefinitionName, dueDate);

			List<DeadLetterJobRepresentation> results = deasLetterJobQuerySearchFactory.searchDeadJobs(businessKey,
					exceptionMessage, processDefinitionName, dueDate, rangeCriteria.offset(), rangeCriteria.limit(),
					new SortCriteria(searchQuery));
			return new DefaultPageRepresentation<>(results, count);
		}

		return new DefaultPageRepresentation<>(new ArrayList<>(), 0);
	}

	private String trimToEmptyWithLike(String str) {
		if (str != null && !str.trim().isEmpty()) {
			return "%" + str + "%";
		}
		return null;
	}

	@Override
	public void retryByActivity(String activityId) {
		log.info("retry jobs by activity {}", activityId);
		managementService.createDeadLetterJobQuery()//
				.elementId(activityId)
				.list().stream()//
				.forEach(this :: retry);

	}

	
	/**
	 * Retry a Job wit setting a deadLettre context
	 * @param job
	 */
	private void retry(final Job job) {
		log.info("Retry job {}", job.getId());
		runtimeService.setVariable(job.getExecutionId(), WorkflowVariables.CONTEXT_DEADLETTER, true);
		managementService.moveDeadLetterJobToExecutableJob(job.getId(), deadLettersResumeRetries);
	}
	
	
}
