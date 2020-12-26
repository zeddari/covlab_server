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
	public OverallStatsRepresentation findKpiByLastUpdated() {
		List<OverallStats> overallStats = overallStatsRepository.getKpiCustomQuery();
		if (Optional.ofNullable(overallStats).isPresent()) {
			return inventoryMapper.toOverallStatsRepres(overallStats.get(0));
		}
		return OverallStatsRepresentation.builder().build();
	}
	@Override
	public List<ServiceDashRep> getQuantitiesHandByCategory() {
	
		List<ServiceDashProjection> list = inventoryRepository.findHandByCategorys();
		List<ServiceDashRep> data = new ArrayList();
		if(list!=null && !list.isEmpty()) {
			for(ServiceDashProjection el : list) {
				ServiceDashRep a =	ServiceDashRep.builder().code_categorie(el.getCategory()).total_quantities_inHand(el.getQuantitiesCategory()).build();
				data.add(a);
				
			}
			return data;
		}
		
		data.add(ServiceDashRep.builder().build());
		return data;
	}
	@Override
	public List<ServiceDashRep> getQuantitiesHandByLocation() {
		
		List<ServiceDashProjection> list = inventoryRepository.findHandByLocation();
		List<ServiceDashRep> data = new ArrayList();
		if(list!=null && !list.isEmpty()) {
			for(ServiceDashProjection el : list) {
				ServiceDashRep a =	ServiceDashRep.builder().outlet_name(el.getLocation()).total_quantities_inHand(el.getQuantitiesLocation()).build();
				data.add(a);
				
			}
			return data;
		}
		
		data.add(ServiceDashRep.builder().build());
		return data;
	}

}
