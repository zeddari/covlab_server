package com.axilog.cov.repository;

import com.axilog.cov.domain.Outlet;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Outlet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OutletRepository extends JpaRepository<Outlet, Long>, JpaSpecificationExecutor<Outlet> {
}
