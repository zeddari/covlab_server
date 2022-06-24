package com.axilog.cov.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.axilog.cov.domain.Task;


/**
 * Spring Data Elasticsearch repository for the {@link Task} entity.
 */
public interface TaskSearchRepository extends ElasticsearchRepository<Task, Long> {
}
