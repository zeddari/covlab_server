package com.axilog.cov.repository;

import com.axilog.cov.domain.DeviceOverviewStats;
import com.axilog.cov.domain.Outlet;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DeviceOverviewStats entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceOverviewStatsRepository extends JpaRepository<DeviceOverviewStats, Long>, JpaSpecificationExecutor<DeviceOverviewStats> {

    List<DeviceOverviewStats>  findByOutletOutletId(Long outletId);
    
    List<DeviceOverviewStats>  findByOutlet(Outlet outlet);
}
