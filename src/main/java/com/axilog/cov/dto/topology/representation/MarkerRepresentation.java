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
public class MarkerRepresentation {
	private String lat;
	private String lng;
	private String  label;
	private String  label2;
	private Boolean draggable;
	private String icon;
	private Integer iconHeight;
	private Integer iconWidth;
	private List<PolylineRepresentation> polylines;
	private String polylineColor;
	private String type;
	private String htmlModal;
	private String info1, info21, info22, info3, info4, info5, info6, info7, info8;	
	private String color;
	private boolean visible;
	private String animation;
	private boolean circleToDisplay;
	private String nodeGeoData;
	Font font;
}
