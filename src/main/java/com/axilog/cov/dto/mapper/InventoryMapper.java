package com.axilog.cov.dto.mapper;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.axilog.cov.domain.Inventory;
import com.axilog.cov.domain.OverallStats;
import com.axilog.cov.dto.representation.InventoryDetail;
import com.axilog.cov.dto.representation.InventoryPdfDetail;
import com.axilog.cov.dto.representation.InventoryRepresentation;
import com.axilog.cov.dto.representation.OverallStatsRepresentation;

@Component
public class InventoryMapper {
	/**
	 * @param inventory
	 * @return
	 */
	public InventoryDetail toInventoryDetail(Inventory inventory) {
		DecimalFormat formatter = (DecimalFormat)NumberFormat.getNumberInstance(Locale.US);
		formatter.applyPattern("##.#");
		return InventoryDetail.builder()
				.inventoryId(inventory.getId())
				.itemCode(inventory.getProduct().getProductCode())
				.description(inventory.getProduct().getDescription())
				.currentBalance(inventory.getCurrent_balance())
				.receivedQuantity(inventory.getReceivedQty())
				.quantitiesInTransit(inventory.getQuantitiesInTransit())
				.uom(inventory.getUom())
				.consumedQty(inventory.getConsumedQty())
				.actualAvgConsumption(inventory.getActualAvgConsumption())
				.reorderLevel(inventory.getReOrderLevel())
				.suggestedQuantity(inventory.getSuggestedQuantity())
				.expectedCoveringDay(inventory.getCapacity())
				.lastUpdatedAt(inventory.getLastUpdatedAt())
				.status(inventory.getStatus())
				.region(inventory.getOutlet().getOutletRegion())
				.outletName(inventory.getOutlet().getOutletName())
				.outletType(inventory.getOutlet().getOutletName())
				.outletAddress(inventory.getOutlet().getOutletAdress())
				.outletLat(inventory.getOutlet().getOutletLat())
				.outletLng(inventory.getOutlet().getOutletLng())
				.category(inventory.getProduct().getCategory().getCategoryCode())
				.temperature(formatter.format(inventory.getProduct().getDeviceOverviewStats() != null ? inventory.getProduct().getDeviceOverviewStats().getTemperature() : 0))
				.nupcoCode(inventory.getProduct().getProductCode())
				.build();
	}
	
	/**
	 * @param inventories
	 * @return
	 */
	public InventoryRepresentation toInventoryRepresentation(List<Inventory> inventories) {
		if (inventories == null) return InventoryRepresentation.builder().build();
		InventoryRepresentation inventoryRepresentation = InventoryRepresentation.builder().build();
		inventoryRepresentation.setInventoryData(new ArrayList<>());
		inventories.forEach(inventory -> {
			inventoryRepresentation.getInventoryData().add(toInventoryDetail(inventory));
		});
		return inventoryRepresentation;
	}
	
	/**
	 * @param overallStats
	 * @return
	 */
	public OverallStatsRepresentation toOverallStatsRepres(OverallStats overallStats) {
		return OverallStatsRepresentation.builder()
				.deliveryOnTimeInFull(overallStats.getDeliveryOnTimeInFull())
				.lastUpdatedAt(overallStats.getLastUpdatedAt())
				.overallOutletPerformanceScore(overallStats.getOverallOutletPerformanceScore())
				.stockoutRatio(overallStats.getStockoutRatio())
				.totalVaccinesConsumed(overallStats.getTotalVaccinesConsumed())
				.totalVaccinesReceivedAtNupco(overallStats.getTotalVaccinesReceivedAtNupco())
				.totalVaccinesReceivedAtOutlets(overallStats.getTotalVaccinesReceivedAtOutlets())
				.warehouseFillingRate(overallStats.getWarehouseFillingRate())
				.wastageVaccines(overallStats.getWastageVaccines())
				.build();
	}
	
	/**
	 * @param inventory
	 * @return
	 */
	public InventoryPdfDetail toPdfDetail(Inventory inventory) {
		return InventoryPdfDetail.builder()
				.code(inventory.getProduct().getProductCode())
				.description(inventory.getProduct().getDescription())
				.uom(inventory.getUom())
				.build();
	}
	
	/**
	 * @param inventories
	 * @return
	 */
	public List<InventoryPdfDetail> toPdfListDetail(List<Inventory> inventories) {
		List<InventoryPdfDetail> inventoryPdfDetails = new ArrayList<>();
		if (inventories == null) return inventoryPdfDetails;
		inventories.forEach(inv -> {
			inventoryPdfDetails.add(toPdfDetail(inv));
		});
		return inventoryPdfDetails;
	}
}
