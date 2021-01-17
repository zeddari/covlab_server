package com.axilog.cov.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.axilog.cov.domain.DynamicApprovalConfig;
import com.axilog.cov.domain.ImportHistory;
import com.axilog.cov.domain.Outlet;

/**
 * Spring Data  repository for the Outlet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImportHistoryRepository extends JpaRepository<ImportHistory, Integer>, JpaSpecificationExecutor<ImportHistory> {

}
