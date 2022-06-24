package com.axilog.cov.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.axilog.cov.domain.Sla;


/**
 * Spring Data Elasticsearch repository for the {@link Sla} entity.
 */
public interface SlaSearchRepository extends ElasticsearchRepository<Sla, Long> {
}
