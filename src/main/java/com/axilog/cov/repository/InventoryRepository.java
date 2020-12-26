package com.axilog.cov.repository;

import com.axilog.cov.domain.Inventory;
import com.axilog.cov.dto.projection.ServiceDashProjection;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Inventory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long>, JpaSpecificationExecutor<Inventory> {

	@Query(value = " select a.category_code, sum(b.quantities_in_hand) from category a, inventory b, product c where b.product_id = c.product_id and a.category_id = c.category_id and b.is_last_instance = 1 group by BY  a.category_code", nativeQuery = true)
	List<ServiceDashProjection> findHandByCategorys();
	@Query(value = " select  a.outlet_name, sum(b.quantities_in_hand) from outlet a, inventory b where a.outlet_id = b.outlet_id and b.is_last_instance = 1 group by a.outlet_name ", nativeQuery = true)
	List<ServiceDashProjection> findHandByLocation();
	
	
}

