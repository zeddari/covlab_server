package com.axilog.cov.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.axilog.cov.domain.Request;


/**
 * Spring Data Elasticsearch repository for the {@link Request} entity.
 */
public interface RequestSearchRepository extends ElasticsearchRepository<Request, Long> {
}
