package com.axilog.cov.dto.representation;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DashBoardRepresentation {

	 List<ChartDetail> dashBoardDetails;
	 List<LineChartDetail> listLineChartDetails;
}
