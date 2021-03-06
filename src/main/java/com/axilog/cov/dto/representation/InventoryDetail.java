package com.axilog.cov.dto.representation;

import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class InventoryDetail {
	 private float inventoryId;
	 private String itemCode;
	 private String nupcoCode;
	 private String description;
	 private Double currentBalance;
	 private Double receivedQuantity;
	 private Double quantitiesInTransit;
	 private String uom;
	 private Double consumedQty;
	 private Double actualAvgConsumption;
	 private String reorderLevel;
	 private Double suggestedQuantity;
	 private Double expectedCoveringDay;
	 
	 @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	 private Date lastUpdatedAt;
	 
	 private String status;
	 private String region;
	 private String outletName;
	 private String outletType;
	 private String outletAddress;
	 private Double outletLat;
	 private Double outletLng;
	 private String category;
	 private String temperature;
	 private Double receivedUserQte; 
	 private Double consumedUserQte;
	 private Double wastage;
	 private Double damage;
	 private Double sample;
	    
	 
}
