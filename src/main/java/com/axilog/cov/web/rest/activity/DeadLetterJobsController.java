package com.axilog.cov.web.rest.activity;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axilog.cov.dto.base.PageRepresentation;
import com.axilog.cov.dto.query.SearchDeadLetterJobsQuery;
import com.axilog.cov.dto.representation.DeadLetterJobRepresentation;
import com.axilog.cov.exception.DeadLetterJobNotFoundException;
import com.axilog.cov.service.activity.api.DeadLettersService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/api/monitoring/deadletter-jobs")
@Api(tags = "Ui")
public class DeadLetterJobsController {

    @Autowired
    private DeadLettersService deadlettersService;

    @PostMapping(value = "/retry/{jobId}")
    public void retry(@PathVariable String jobId) throws DeadLetterJobNotFoundException {
        deadlettersService.retry(jobId);
    }

    @PostMapping(value = "/retry/all")
    public void retryAll() {
        deadlettersService.retryAll();
    }

    @PostMapping(value = "/retry/activity/{activityId}")
    public void retryByActivity(@PathVariable String activityId) {
        deadlettersService.retryByActivity(activityId);
    }

    @DeleteMapping(value = "/{jobId}")
    public void delete(@PathVariable String jobId) throws DeadLetterJobNotFoundException {
        deadlettersService.delete(jobId);
    }

    @DeleteMapping(value = "/all")
    public void deleteAll() {
        deadlettersService.deleteAll();
    }

    @PostMapping
    public PageRepresentation<DeadLetterJobRepresentation> findAll(@Valid @RequestBody SearchDeadLetterJobsQuery searchQuery) {
        return deadlettersService.search(searchQuery);
    }
}
