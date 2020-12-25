package com.axilog.cov.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.axilog.cov.domain.OverallStats;

/**
 * Spring Data  repository for the OverallStats entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OverallStatsRepository extends JpaRepository<OverallStats, Long>, JpaSpecificationExecutor<OverallStats> {

	@Query(value = "SELECT * FROM overall_stats where Last_Updated_At = (select max(Last_Updated_At) from overall_stats)", nativeQuery = true)
	public List<OverallStats> getKpiCustomQuery();
}
