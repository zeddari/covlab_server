package com.axilog.cov.repository;

import com.axilog.cov.domain.Drivers;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Tickets entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DriversRepository extends JpaRepository<Drivers, Long>, JpaSpecificationExecutor<Drivers> {
}
