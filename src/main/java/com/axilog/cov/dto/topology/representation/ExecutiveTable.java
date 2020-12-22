package com.axilog.cov.dto.topology.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecutiveTable {

	private String accessCsTraffic;
	private String accessPsTraffic;
	private String bcInboundTraffic;
	private String bcOutboundTraffic;
	private String overallAccessibility;
	private String downAccessSites;
	private String downTransportNodes;
	
}
