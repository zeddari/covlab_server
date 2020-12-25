package com.axilog.cov.dto.topology.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PolylineRepresentation {
	private String latSrc;
	private String lngSrc;
	private String latDest;
	private String lngDest;
	private String color;
	private String textLinkModal;
}