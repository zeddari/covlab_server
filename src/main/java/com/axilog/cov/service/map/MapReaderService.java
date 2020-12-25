package com.axilog.cov.service.map;

import com.axilog.cov.dto.topology.representation.MapRepresentation;
import com.axilog.cov.exception.MapDataNotFoundException;
import com.axilog.cov.exception.TopologyDataNotFoundException;

public interface MapReaderService {

	
	/**
	 * @return
	 * @throws MapDataNotFoundException
	 * @throws TopologyDataNotFoundException
	 */
	public MapRepresentation getMapData() throws MapDataNotFoundException, TopologyDataNotFoundException;
	
	
	
}
