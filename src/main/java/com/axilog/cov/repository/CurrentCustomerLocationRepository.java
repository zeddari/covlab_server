package com.axilog.cov.repository;

import com.axilog.cov.domain.Category;
import com.axilog.cov.domain.CategoryType;
import com.axilog.cov.domain.CurrentCustomerLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CurrentCustomerLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CurrentCustomerLocationRepository extends JpaRepository<CurrentCustomerLocation, Long>, JpaSpecificationExecutor<Category> {
    public List<CurrentCustomerLocation> findByQuotationId(String quotationId);
}
