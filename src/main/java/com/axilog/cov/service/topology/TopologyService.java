package com.axilog.cov.service.topology;

import com.axilog.cov.dto.topology.representation.TopologyRepresentation;
import com.axilog.cov.exception.TopologyDataNotFoundException;

public interface TopologyService {

	public TopologyRepresentation buildTopologyData() throws TopologyDataNotFoundException; 
}
