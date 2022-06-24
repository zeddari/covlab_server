package com.axilog.cov.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.axilog.cov.domain.RequestStatus;


/**
 * Spring Data Elasticsearch repository for the {@link RequestStatus} entity.
 */
public interface RequestStatusSearchRepository extends ElasticsearchRepository<RequestStatus, Long> {
}
