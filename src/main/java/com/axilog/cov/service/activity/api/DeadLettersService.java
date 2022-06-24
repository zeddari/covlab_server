package com.axilog.cov.service.activity.api;

import org.flowable.job.api.Job;

import com.axilog.cov.dto.base.PageRepresentation;
import com.axilog.cov.dto.query.SearchDeadLetterJobsQuery;
import com.axilog.cov.dto.representation.DeadLetterJobRepresentation;
import com.axilog.cov.exception.DeadLetterJobNotFoundException;

public interface DeadLettersService {

    void retry(String jobId) throws DeadLetterJobNotFoundException;
    void retryAll();
    void delete(String jobId) throws DeadLetterJobNotFoundException;

    Job findById(String jobId) throws DeadLetterJobNotFoundException;
    
    void deleteAll();
    PageRepresentation<DeadLetterJobRepresentation> search(SearchDeadLetterJobsQuery searchQuery);

    void retryByActivity(String activityId);
}
