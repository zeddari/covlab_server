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
public class MapRepresentation {

	private List<MarkerRepresentation> markers;
	private List<PolylineRepresentation> polylines;
	private KpiTable kpiTable;
	private SummaryRepresentationForCircle sumRepForCircle;
	private ExecutiveTable executiveTable;
	private String geoData;
	private String geoDataService;
	private String geoDataOther;
}
