package com.axilog.cov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.axilog.cov.domain.Sla;

/**
 * Spring Data  repository for the Sla entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SlaRepository extends JpaRepository<Sla, Long>, JpaSpecificationExecutor<Sla> {

	Sla findByRequestIdAndContext(Long id, String context);
}
