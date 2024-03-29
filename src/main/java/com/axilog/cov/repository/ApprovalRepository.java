package com.axilog.cov.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.axilog.cov.domain.Category;
import com.axilog.cov.domain.DynamicApprovalConfig;

/**
 * Spring Data  repository for the Category entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApprovalRepository extends JpaRepository<DynamicApprovalConfig, Long>, JpaSpecificationExecutor<Category> {

	public DynamicApprovalConfig findByStartStatus(Boolean startStatus);
	public DynamicApprovalConfig findByFinalStatus(Boolean endStatus);
	public List<DynamicApprovalConfig> findByCurrentStepStatus(String currentStatus);
	public DynamicApprovalConfig findByCurrentStepStatusAndOutlet(String currentStatus, String outlet);
}
