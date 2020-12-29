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

	List<Inventory> findByStatusIn(List<String> status);
	
}
