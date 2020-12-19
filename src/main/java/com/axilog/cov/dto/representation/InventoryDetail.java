package com.axilog.cov.dto.representation;

import java.time.LocalDate;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class InventoryDetail {
	 private float inventoryId;
	 private String itemCode;
	 private String description;
	 private Double quantitiesInHand;
	 private Double quantitiesInTransit;
	 private String uom;
	 private Double actualDailyConsumption;
	 private Double actualAvgConsumption;
	 private String reorderLevel;
	 private Double suggestedQuantity;
	 private Double expectedCoveringDay;
	 private LocalDate lasterUpdated;
	 private String status;
	 private String region;
	 private String outletName;
	 private String outletType;
	 private String outletAddress;
	 private Double outletLat;
	 private Double outletLng;
	 private String category;
	 private Double temperature;
}
