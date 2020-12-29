package com.axilog.cov.dto.mapper;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.axilog.cov.domain.PoStatus;
import com.axilog.cov.domain.PurchaseOrder;
import com.axilog.cov.dto.representation.PurchaseOrderDetail;
import com.axilog.cov.dto.representation.PurchaseOrderRepresentation;

@Component
public class PurchaseOrderMapper {
	/**
	 * @param PurchaseOrder
	 * @return
	 */
	public PurchaseOrderDetail toPurchaseOrderDetail(PurchaseOrder purchaseOrder) {
		return PurchaseOrderDetail.builder()
				.PoNo(purchaseOrder.getOrderNo())
				.quantityPo(purchaseOrder.getQuantity())
				.createdByPo(purchaseOrder.getCreatedBy())
				.createdOnPO(purchaseOrder.getCreatedOn())
				.deliveredDatePo(purchaseOrder.getDeliveredDate())
				.updatedAtPo(purchaseOrder.getUpdatedAt())
				.createdAtPo(purchaseOrder.getCreatedAt())
				.region(purchaseOrder.getOutlet().getOutletRegion())
				.statusPo(purchaseOrder.getPoStatuses().stream().max(Comparator.comparing(PoStatus::getUpdatedAt)).get().getStatus())
				.build();
	}
	
	/**
	 * @param Liste purchaseOrder 
	 * @return
	 */
	public PurchaseOrderRepresentation toPurchaseRepresentation(List<PurchaseOrder> orders) {
		if (orders == null) return PurchaseOrderRepresentation.builder().build();
		PurchaseOrderRepresentation purchaseOrderRepresentation = PurchaseOrderRepresentation.builder().build();
		purchaseOrderRepresentation.setPurchaseOrderData(new ArrayList<>());
		orders.forEach(order -> {
			purchaseOrderRepresentation.getPurchaseOrderData().add(toPurchaseOrderDetail(order));
		});
		return purchaseOrderRepresentation;
	}
	
}

