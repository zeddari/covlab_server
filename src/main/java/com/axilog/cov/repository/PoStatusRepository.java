package com.axilog.cov.repository;

import com.axilog.cov.domain.PoStatus;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PoStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PoStatusRepository extends JpaRepository<PoStatus, Long>, JpaSpecificationExecutor<PoStatus> {
}
