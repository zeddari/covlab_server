package com.axilog.cov.service.impl.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axilog.cov.dto.mapper.GMapMapper;
import com.axilog.cov.dto.topology.representation.MapRepresentation;
import com.axilog.cov.exception.MapDataNotFoundException;
import com.axilog.cov.exception.TopologyDataNotFoundException;
import com.axilog.cov.service.map.MapReaderService;
import com.axilog.cov.service.topology.TopologyService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MapReaderServiceImpl implements MapReaderService {

	@Autowired
	private TopologyService topologyReaderService;
	@Autowired
	private GMapMapper gMapMapper;
	/**
	 * @throws TopologyDataNotFoundException 
	 *
	 */
	
	@Override
	public MapRepresentation getMapData() throws MapDataNotFoundException, TopologyDataNotFoundException {
		return gMapMapper.toMap(topologyReaderService.buildTopologyData());
	}

	
	@Override
	public MapRepresentation getMapDataWithParam(String statusOrTemperature) throws MapDataNotFoundException, TopologyDataNotFoundException {
		return gMapMapper.toMap(topologyReaderService.buildTopologyDataWithParam(statusOrTemperature));
	}

	
}