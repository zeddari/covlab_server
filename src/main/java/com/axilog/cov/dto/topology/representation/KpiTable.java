package com.axilog.cov.dto.topology.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KpiTable {

	private Long numberOfVaccine;
	private Long consumedVaccine;
	private Integer numberOfOutlets;

	
}
