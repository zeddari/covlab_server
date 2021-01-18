package com.axilog.cov.repository;

import com.axilog.cov.domain.Inventory;
import com.axilog.cov.domain.Outlet;
import com.axilog.cov.domain.Product;
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

	List<Inventory> findByStatusIn(List<String> status);
	List<Inventory> findByStatusInAndIsLastInstance(List<String> status, Boolean isLastInstance);
	List<Inventory> findByStatusInAndIsLastInstanceAndCapacityLessThan(List<String> status, Boolean isLastInstance, Double capapcity);
	List<Inventory> findByOutletAndProductAndIsLastInstance(Outlet outlet, Product product, Boolean isLastInstance);
}
