package com.axilog.cov.dto.representation;

import java.util.List;
import java.util.Map;

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
	
	private String subsCodesCol;
	private String subsDescription;
	private String subsCategory;
	
	private List<String> subsCodes;
	private Map<String, String> subsMapDescriptions;
	private Map<String, String> subsMapCategories;
	
	private Double quantity;
	private Double receivedQuantity;
	private Double balance;
	private String uom;
	
}
