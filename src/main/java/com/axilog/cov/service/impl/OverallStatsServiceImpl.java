package com.axilog.cov.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axilog.cov.domain.OverallStats;
import com.axilog.cov.dto.mapper.InventoryMapper;
import com.axilog.cov.dto.projection.OutletOverviewStatsProjection;
import com.axilog.cov.dto.projection.ServiceDashProjection;
import com.axilog.cov.dto.representation.ChartDetail;
import com.axilog.cov.dto.representation.OverallStatsDetail;
import com.axilog.cov.dto.representation.OverallStatsRepresentation;
import com.axilog.cov.dto.representation.SeriesDetail;
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
	
	@Autowired
	private  InventoryRepository inventoryRepository;

	
	@Override
	public OverallStatsRepresentation findKpiByLastUpdated(String outlet) {
		if (outlet == null) {
				
		}
		else {
		
		}
		
		return null;
	}



}
