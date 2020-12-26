package com.axilog.cov.service;

import java.util.List;

import com.axilog.cov.domain.Category;
import com.axilog.cov.dto.representation.OverallStatsRepresentation;
import com.axilog.cov.dto.representation.ServiceDashRep;

/**
 * Service Interface for managing {@link Category}.
 */
public interface OverallStatsService {

	/**
	 * @return
	 */
		public OverallStatsRepresentation findKpiByLastUpdated();
	   public List<ServiceDashRep> getQuantitiesHandByCategory();
	    public List<ServiceDashRep> getQuantitiesHandByLocation();
	
}
