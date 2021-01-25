package com.axilog.cov.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.axilog.cov.domain.DynamicApprovalConfig;
import com.axilog.cov.domain.GrnHistory;
import com.axilog.cov.domain.ImportHistory;
import com.axilog.cov.domain.Outlet;
import com.axilog.cov.domain.PersistentAuditEvent;
import com.axilog.cov.dto.representation.ImportHistoryDetail;

/**
 * Spring Data  repository for the Grn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrnHistoryRepository extends JpaRepository<GrnHistory, Long>, JpaSpecificationExecutor<GrnHistory> {
	    
}
