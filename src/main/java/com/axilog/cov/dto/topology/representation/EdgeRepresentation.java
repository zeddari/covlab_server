package com.axilog.cov.dto.topology.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EdgeRepresentation {
	private String id;
	private Integer from;
	private Integer to;
	private String title;
	private Integer length;
	private Integer width;
	private String color;
	private String latSrc;
	private String lngSrc;
	private String latDest;
	private String lngDest;
	private String htmlLinkModal;
	private String textLinkModal;
	private String type;
	Font font;
}
