package com.axilog.cov.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.axilog.cov.domain.OverallStats;
import com.axilog.cov.domain.OverallStatsOutlet;
import com.axilog.cov.dto.projection.OutletOverviewProjection;

/**
 * Spring Data  repository for the OverallStats entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OverallStatsOutletRepository extends JpaRepository<OverallStatsOutlet, Long>, JpaSpecificationExecutor<OverallStatsOutlet> {

}
