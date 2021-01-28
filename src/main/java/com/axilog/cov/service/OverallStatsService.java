package com.axilog.cov.service;

import com.axilog.cov.domain.Category;
import com.axilog.cov.dto.representation.OverallStatsRepresentation;

/**
 * Service Interface for managing {@link Category}.
 */
public interface OverallStatsService {

	/**
	 * @return
	 */
		public OverallStatsRepresentation findKpiByLastUpdated(String outlet);
}
