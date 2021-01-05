package com.axilog.cov.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axilog.cov.domain.OverallStats;
import com.axilog.cov.dto.mapper.InventoryMapper;
import com.axilog.cov.dto.projection.ServiceDashProjection;
import com.axilog.cov.dto.representation.OverallStatsRepresentation;
import com.axilog.cov.dto.representation.ServiceDashRep;
import com.axilog.cov.repository.InventoryRepository;
import com.axilog.cov.repository.OverallStatsRepository;
import com.axilog.cov.service.OverallStatsService;

@Service
@Transactional
public class OverallStatsServiceImpl implements OverallStatsService {

	@Autowired
	private OverallStatsRepository overallStatsRepository;
	
	@Autowired
	private InventoryMapper inventoryMapper;
	private  InventoryRepository inventoryRepository;
	
	@Override
	public OverallStatsRepresentation findKpiByLastUpdated(String outlet) {
		if (outlet == null) {
			List<OverallStats> overallStats = overallStatsRepository.getKpiCustomQuery();
			if (Optional.ofNullable(overallStats).isPresent()) {
				return inventoryMapper.toOverallStatsRepres(overallStats.get(0));
			}
		}
		else {
			List<OverallStats> overallStats = overallStatsRepository.getKpiOutletCustomQuery(outlet);
			if (Optional.ofNullable(overallStats).isPresent()) {
				return inventoryMapper.toOverallStatsRepres(overallStats.get(0));
			}
		}
		
		return OverallStatsRepresentation.builder().build();
	}



}
