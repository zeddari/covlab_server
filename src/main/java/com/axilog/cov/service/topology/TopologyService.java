package com.axilog.cov.service.topology;

import java.util.List;

import com.axilog.cov.dto.topology.representation.NodeIdListRepresentation;
import com.axilog.cov.dto.topology.representation.TopologyRepresentation;
import com.axilog.cov.exception.TopologyDataNotFoundException;

public interface TopologyService {

	public TopologyRepresentation buildTopologyData() throws TopologyDataNotFoundException;

	public TopologyRepresentation buildTopologyDataWithParam(String statusOrTemperature) throws TopologyDataNotFoundException;

	public List<NodeIdListRepresentation> getDistinctNodeId(); 
}
