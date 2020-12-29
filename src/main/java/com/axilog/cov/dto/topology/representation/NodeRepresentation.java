package com.axilog.cov.dto.topology.representation;

import com.axilog.cov.dto.representation.InventoryDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NodeRepresentation {
	
	
	private Integer id;
	private String label;
	private String label2;
	private String image;
	private String  shape;
	private String nodeOnMapLink;
	private String lat;
	private String lng;
	private String nodeGeoData;
	private String nodeType;
	private String group;
	private Boolean fixed;
	private Integer x;
	private Integer y;
	private String title;
	private Font font;
	private String region;
	private String parentRegion;
	private InventoryDetail inventoryDetail;

}
