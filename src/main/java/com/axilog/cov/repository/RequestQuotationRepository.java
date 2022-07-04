package com.axilog.cov.repository;

import com.axilog.cov.domain.RequestQuotation;
import com.axilog.cov.domain.Product;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RequestQuotation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestQuotationRepository extends JpaRepository<RequestQuotation, Long>, JpaSpecificationExecutor<RequestQuotation> {

	List<RequestQuotation> findByRequestQuotationId(Long requestQuotationId);

	
}
