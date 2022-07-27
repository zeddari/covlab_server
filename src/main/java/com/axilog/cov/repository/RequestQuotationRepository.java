package com.axilog.cov.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.axilog.cov.domain.RequestQuotation;

/**
 * Spring Data  repository for the RequestQuotation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestQuotationRepository extends JpaRepository<RequestQuotation, Long>, JpaSpecificationExecutor<RequestQuotation> {

	List<RequestQuotation> findByRequestQuotationId(Long requestQuotationId);

	RequestQuotation findByRequestQuotationId(String requestQuotationId);
	

}
