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
public interface OverallStatsRepository extends JpaRepository<OverallStats, Long>, JpaSpecificationExecutor<OverallStats> {

	@Query(value = "SELECT * FROM overall_stats where Last_Updated_At = (select max(Last_Updated_At) from overall_stats)", nativeQuery = true)
	public List<OverallStats> getKpiCustomQuery();
	
	@Query(value = "SELECT t1.current_balance  as currentBalance, t1.delivery_on_time_in_full as deliveryOnTimeInFull , t1.last_updated_at as lastUpdatedAt, t1.outlet_name as outletName, t1.overall_outlet_performance_score as overallOutletPerformanceScore  ,     t1.stockout_ratio  as stockoutRatio, t1.total_vaccines_consumed as totalVaccinesConsumed, t1.total_vaccines_received_at_nupco  as totalVaccinesReceivedAtNupco, t1.total_vaccines_received_at_outlets  as totalVaccinesReceivedAtOutlets, t1.warehouse_filling_rate as warehouseFillingRate, t1.wastage_vaccines as wastageVaccines  FROM outlet_overview_stats t1 where last_updated_at = (select max(last_updated_at) from outlet_overview_stats where outlet_name =:outlet) and outlet_name =:outlet", nativeQuery = true)
	public List<OutletOverviewProjection> getKpiOutletCustomQuery(@Param("outlet") String outlet);
	
	@Query(value = "SELECT * FROM outlet_overview_stats where last_updated_at = (select max(last_updated_at) from outlet_overview_stats)", nativeQuery = true)
	public List<OverallStatsOutlet> getKpiAllOutletCustomQuery();
	
}
