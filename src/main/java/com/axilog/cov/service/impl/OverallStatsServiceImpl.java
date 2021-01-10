package com.axilog.cov.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axilog.cov.domain.OverallStats;
import com.axilog.cov.dto.mapper.InventoryMapper;
import com.axilog.cov.dto.projection.OutletOverviewProjection;
import com.axilog.cov.dto.representation.OverallStatsRepresentation;
import com.axilog.cov.repository.OverallStatsOutletRepository;
import com.axilog.cov.repository.OverallStatsRepository;
import com.axilog.cov.service.OverallStatsService;

@Service
@Transactional
public class OverallStatsServiceImpl implements OverallStatsService {

	@Autowired
	private OverallStatsRepository overallStatsRepository;
	
	@Autowired
	private OverallStatsOutletRepository overallStatsOutletRepository;
	
	@Autowired
	private InventoryMapper inventoryMapper;
	
	@Override
	public OverallStatsRepresentation findKpiByLastUpdated(String outlet) {
		List<OutletOverviewProjection> overallStatsOutlet = overallStatsRepository.getKpiOutletCustomQuery(outlet);
//		Optional<OverallStatsOutlet> optionalStatOutlet = overallStatsOutlet.stream().filter(stat -> stat.getOutletName().equals(outlet)).max(Comparator.comparing(OverallStatsOutlet::getLastUpdatedAt));
		if (overallStatsOutlet != null && overallStatsOutlet.size() > 0) {
			return inventoryMapper.toOverallStatsRepresOutlet(overallStatsOutlet.get(0));
		}
		 
		return OverallStatsRepresentation.builder().build();
	}



}
