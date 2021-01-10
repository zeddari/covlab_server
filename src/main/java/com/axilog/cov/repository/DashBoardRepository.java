package com.axilog.cov.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.axilog.cov.domain.Inventory;
import com.axilog.cov.dto.projection.DashInventoryComProjection;
import com.axilog.cov.dto.projection.DashInventoryStockAllOutletProjection;
import com.axilog.cov.dto.projection.DashInventoryStockProjection;
import com.axilog.cov.dto.projection.ServiceDashProjection;

/**
 * Spring Data  repository for the Inventory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DashBoardRepository extends JpaRepository<Inventory, Long>, JpaSpecificationExecutor<Inventory> {

	@Query(value = "SELECT * FROM kpi_inventory_compo where outlet_name =:outlet ", nativeQuery = true)
	List<DashInventoryComProjection> getInventoryCompo(@Param ( "outlet" ) String outlet);
	 
	@Query(value = "select * from Inventory ", nativeQuery = true)
	List<ServiceDashProjection>  getRotStock();
	
	@Query(value = "SELECT * FROM kpi_inventory_stock_outlet where outlet_name =:outlet ", nativeQuery = true)
	List<DashInventoryStockProjection>  getStockOutlet(@Param ( "outlet" ) String outlet);
	
	
	@Query(value = "SELECT * FROM kpi_stock_by_outlet ", nativeQuery = true)
	List<DashInventoryStockAllOutletProjection>  getStockForAllOutlet();
	
	
	 
	
}
