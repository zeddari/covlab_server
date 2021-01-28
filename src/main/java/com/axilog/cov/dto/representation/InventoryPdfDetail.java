package com.axilog.cov.dto.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryPdfDetail {

	private String code;
	private String description;
	private String category;
	private Double quantity;
	private Double receivedQuantity;
	private String uom;
	
}
