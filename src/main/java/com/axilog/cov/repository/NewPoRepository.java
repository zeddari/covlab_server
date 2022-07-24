package com.axilog.cov.repository;

import com.axilog.cov.domain.Category;
import com.axilog.cov.domain.NewPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the NewPo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NewPoRepository extends JpaRepository<NewPo, Long>, JpaSpecificationExecutor<Category> {
    public List<NewPo> findByQuotationIdAndAndPoId(String quotationId, String poId);
}
