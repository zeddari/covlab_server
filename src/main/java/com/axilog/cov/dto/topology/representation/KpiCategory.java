package com.axilog.cov.dto.topology.representation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KpiCategory {

	private String categoryCode;
	private String categoryLabel;
	private List<KpiData> kpiDatas;
}
