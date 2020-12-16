package com.axilog.cov.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.axilog.cov.domain.Inventory;
import com.axilog.cov.dto.representation.InventoryDetail;
import com.axilog.cov.dto.representation.InventoryRepresentation;

@Component
public class InventoryMapper {
	/**
	 * @param inventory
	 * @return
	 */
	public InventoryDetail toInventoryDetail(Inventory inventory) {
		return InventoryDetail.builder()
				.inventoryId(inventory.getInventoryId())
				.itemCode(inventory.getProduct().getProductCode())
				.description(inventory.getProduct().getDescription())
				.quantitiesInHand(inventory.getQuantitiesInHand())
				.quantitiesInTransit(inventory.getQuantitiesInTransit())
				.uom(inventory.getUom())
				.actualDailyConsumption(inventory.getActualDailyConsumption())
				.actualAvgConsumption(inventory.getActualAvgConsumption())
				.reorderLevel(inventory.getReOrderLevel())
				.suggestedQuantity(inventory.getSuggestedQuantity())
				.expectedCoveringDay(inventory.getExpectedCoveringDay())
				.lasterUpdated(inventory.getLastUpdatedAt())
				.status(inventory.getStatus())
				.region(inventory.getOutlet().getOutletRegion())
				.outletName(inventory.getOutlet().getOutletName())
				.outletType(inventory.getOutlet().getOutletName())
				.outletAddress(inventory.getOutlet().getOutletAdress())
				.outletLat(inventory.getOutlet().getOutletLat())
				.outletLng(inventory.getOutlet().getOutletLng())
				.category(inventory.getProduct().getCategory().getDescriptionCategory())
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
	
}
