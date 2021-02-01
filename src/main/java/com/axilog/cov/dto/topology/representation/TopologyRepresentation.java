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
public class TopologyRepresentation {

	private List<NodeRepresentation> nodes;
	private List<EdgeRepresentation> edges;
	private KpiTable kpiTable;
	private ExecutiveTable executiveTable;
	private String geoData;
	private String geoDataService;
	private String geoDataOther;
	private List<ColoredRegionRepresentation> regionsColor;
}
