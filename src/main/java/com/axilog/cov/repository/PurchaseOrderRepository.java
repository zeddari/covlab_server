package com.axilog.cov.repository;

import com.axilog.cov.domain.PurchaseOrder;
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
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long>, JpaSpecificationExecutor<PurchaseOrder> {

	List<PurchaseOrder> findByOrderNo(Long orderNo);
	
	@Query(value = "SELECT * FROM kpi_delivery_category_outlet where outlet_name =:outlet and category =:category", nativeQuery = true)
	List<DashInventoryStockProjection> getDeliveryOutletCategory(@Param ( "outlet" ) String outlet, @Param ( "category" ) String category);

	PoUpdateRepresentation save(Optional<PurchaseOrder> purchaseOrders);
}
