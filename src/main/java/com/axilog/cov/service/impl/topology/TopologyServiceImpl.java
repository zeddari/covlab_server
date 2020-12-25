package com.axilog.cov.service.impl.topology;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axilog.cov.domain.Inventory;
import com.axilog.cov.dto.mapper.TopologyMapper;
import com.axilog.cov.dto.topology.representation.EdgeRepresentation;
import com.axilog.cov.dto.topology.representation.NodeRepresentation;
import com.axilog.cov.dto.topology.representation.TopologyRepresentation;
import com.axilog.cov.exception.TopologyDataNotFoundException;
import com.axilog.cov.service.InventoryService;
import com.axilog.cov.service.topology.TopologyService;

@Service
public class TopologyServiceImpl implements TopologyService {

	@Autowired
	private InventoryService inventoryService;
	
	
	
	@Override
	public TopologyRepresentation buildTopologyData() throws TopologyDataNotFoundException {
		/** get all network elements */
		List<Inventory> networkElements = inventoryService.findAll();
		List<NodeRepresentation> nodes = new ArrayList<NodeRepresentation>();
		List<EdgeRepresentation> edges = new ArrayList<>();
		networkElements.forEach(ne -> {
			nodes.add(TopologyMapper.toNodeRepresentation(ne));
			edges.add(TopologyMapper.toEdgeRepresentation(ne, 70006));
		});
	
		return TopologyRepresentation.builder().nodes(nodes).edges(edges).build();
	}

}
