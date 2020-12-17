package com.axilog.cov.repository;

import com.axilog.cov.domain.StatusPO;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StatusPO entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatusPORepository extends JpaRepository<StatusPO, Long>, JpaSpecificationExecutor<StatusPO> {
}
