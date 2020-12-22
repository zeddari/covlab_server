package com.axilog.cov.dto.topology.representation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NodeRepresentation {
	
	
	private Integer id;
	private String label;
	private String label2;
	private String image;
	private String  shape;
	private String htmlModal;
	private String textModal;
	private String nodeOnMapLink;
	private String info1, info21, info22, info3, info4, info5, info6, info7, info8;	
	private String lat;
	private String lng;
	private String nodeGeoData;
	private String nodeType;
	private String group;
	private Boolean fixed;
	private Integer x;
	private Integer y;
	private String title;
	private String pathToUpe;
	private String pathUpeToIgw;
	private Font font;
	private List<KpiCategory> kpiCategories;

}
