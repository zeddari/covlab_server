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
				.poNo(purchaseOrder.getOrderNo())
				.quantity(purchaseOrder.getQuantity())
				.createdBy(purchaseOrder.getCreatedBy())
				.createdOn(purchaseOrder.getCreatedOn())
				.deliveredDate(purchaseOrder.getDeliveredDate())
				.updatedAt(purchaseOrder.getUpdatedAt())
				.createdAt(purchaseOrder.getCreatedAt())
				.outlet(purchaseOrder.getOutlet() != null ? purchaseOrder.getOutlet().getOutletName() : null)
				.status(purchaseOrder.getPoStatuses().stream().max(Comparator.comparing(PoStatus::getUpdatedAt)).get().getStatus())
				.data(purchaseOrder.getData())
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

