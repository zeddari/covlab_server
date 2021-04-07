package com.axilog.cov.repository;

import com.axilog.cov.domain.PurchaseOrder;
import com.axilog.cov.domain.PurchaseOrderHistory;
import com.axilog.cov.dto.projection.DashInventoryStockProjection;
import com.axilog.cov.dto.projection.ServiceDashProjection;
import com.axilog.cov.dto.representation.PoUpdateRepresentation;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PurchaseOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchaseOrderHistoryRepository extends JpaRepository<PurchaseOrderHistory, Long>, JpaSpecificationExecutor<PurchaseOrderHistory> {

	List<PurchaseOrderHistory> findByOrderNo(Long orderNo);
	
}