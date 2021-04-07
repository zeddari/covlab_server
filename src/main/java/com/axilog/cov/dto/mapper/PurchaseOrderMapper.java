package com.axilog.cov.dto.mapper;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.axilog.cov.domain.GrnHistory;
import com.axilog.cov.domain.PoReport;
import com.axilog.cov.domain.PoStatus;
import com.axilog.cov.domain.PurchaseOrder;
import com.axilog.cov.domain.PurchaseOrderHistory;
import com.axilog.cov.dto.representation.GrnHistoryDetail;
import com.axilog.cov.dto.representation.GrnHistoryRepresentation;
import com.axilog.cov.dto.representation.PoReportDetail;
import com.axilog.cov.dto.representation.PoReportRepresentation;
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
				.id(purchaseOrder.getId())
				.poNo(purchaseOrder.getOrderNo())
				.quantity(purchaseOrder.getQuantity())
				.createdBy(purchaseOrder.getCreatedBy())
				.createdOn(purchaseOrder.getCreatedOn())
				.deliveredDate(purchaseOrder.getDeliveredDate())
				.updatedAt(purchaseOrder.getUpdatedAt())
				.createdAt(purchaseOrder.getCreatedAt())
				.outlet(purchaseOrder.getOutlet() != null ? purchaseOrder.getOutlet().getOutletName() : null)
				.status(purchaseOrder.getPoStatuses().stream().max(Comparator.comparing(PoStatus::getUpdatedAt)).isPresent() ? purchaseOrder.getPoStatuses().stream().max(Comparator.comparing(PoStatus::getUpdatedAt)).get().getStatus() : null)
				.approvalOwner(purchaseOrder.getApprovalOwner())
				.approvalTime(purchaseOrder.getApprovalTime())
				.approvalReceivingTime(purchaseOrder.getApprovalReceivingTime())
				//.data(Base64.getEncoder().encodeToString(purchaseOrder.getData() != null ? purchaseOrder.getData() : "".getBytes()))
				.build();
	}
	/**
	 * @param  PoReport
	 * @return
	 */
	public PoReportDetail toPoReportDetail(PoReport poReport) {
		return PoReportDetail.builder()
					.item(poReport.getItem())
					.description(poReport.getDescription())
					.uom(poReport.getUom())
					.poOriginalQty(poReport.getPoOriginalQty())
			      	.poReceivedQty(poReport.getPoReceivedQty())
			      	.poBalanceQty(poReport.getPoBalanceQty())
			      	.etaOfDelivery(poReport.getEtaOfDelivery())
			      	.outlet(poReport.getOutlet())
			      	.build();
	}
	
	/**
	 * @param  GrnHistory
	 * @return
	 */
	public GrnHistoryDetail toGrnHistoryDetail(GrnHistory grnHistory) {
		return GrnHistoryDetail.builder()
				.id(grnHistory.getId())
				.grnNumber(grnHistory.getGrnNumber())
				.createdAt(grnHistory.getCreatedAt())
				.createdBy(grnHistory.getCreatedBy())
				.status(grnHistory.getStatus())
				.productCode(grnHistory.getProductCode())
				.uom(grnHistory.getUom())
				.category(grnHistory.getCategory())
				.description(grnHistory.getDescription())
				.poQuantity(grnHistory.getPoQuantity())
				.received(grnHistory.getReceived())
				.outletName(grnHistory.getOutletName())
				.orderNo(grnHistory.getOrderNo())
				.substituteCode(grnHistory.getSubsCode())
				.substituteCategory(grnHistory.getSubsCategory())
				.substituteDescription(grnHistory.getSubsDescription())
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
	
	
	public GrnHistoryRepresentation toGrnHistoryRepresentation(List<GrnHistory> grnHistories) {
		if (grnHistories == null) return GrnHistoryRepresentation.builder().build();
		GrnHistoryRepresentation grnHistoryRepresentation = GrnHistoryRepresentation.builder().build();
		grnHistoryRepresentation.setGrnHistoryData(new ArrayList<>());
		grnHistories.forEach(grnHistory -> {
			grnHistoryRepresentation.getGrnHistoryData().add(toGrnHistoryDetail(grnHistory));
		});
		return grnHistoryRepresentation;
	}
	
	public PoReportRepresentation toPoReportRepresentation(List <PoReport> poReports) {
		if (poReports == null) return PoReportRepresentation.builder().build();
		PoReportRepresentation poReportRepresentation = PoReportRepresentation.builder().build();
		poReportRepresentation.setPoReportData(new ArrayList<>());
		poReports.forEach(poReport -> {
		poReportRepresentation.getPoReportData().add(toPoReportDetail(poReport));
		});
		return poReportRepresentation;
	}
	
	public PurchaseOrderHistory toHistory(PurchaseOrder purchaseOrder) {
		return PurchaseOrderHistory.builder()
				.approvalOwner(purchaseOrder.getApprovalOwner())
				.approvalReceivingTime(purchaseOrder.getApprovalReceivingTime())
				.approvalTime(purchaseOrder.getApprovalTime())
				.createdAt(purchaseOrder.getCreatedAt())
				.createdBy(purchaseOrder.getCreatedBy())
				.createdOn(purchaseOrder.getCreatedOn())
				.data(purchaseOrder.getData())
				.dataXlsx(purchaseOrder.getDataXlsx())
				.deliveredDate(purchaseOrder.getDeliveredDate())
				.hotJson(purchaseOrder.getHotJson())
				.orderNo(purchaseOrder.getOrderNo())
				.outlet(purchaseOrder.getOutlet())
				.poStatuses(purchaseOrder.getPoStatuses())
				.quantity(purchaseOrder.getQuantity())
				.updatedAt(purchaseOrder.getUpdatedAt())
				.build();
	}
}


