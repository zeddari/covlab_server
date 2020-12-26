package com.axilog.cov.web.rest.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axilog.cov.dto.representation.OverallStatsRepresentation;
import com.axilog.cov.exception.TopologyDataNotFoundException;
import com.axilog.cov.service.OverallStatsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/inventory/kpi")
@Api(value = "Topology Rest Api", description = "Operations pertaining to Topology Apis")
public class DashboardController {

	@Autowired
	private OverallStatsService overallStatsService;

	/**
	 * @return
	 * @throws TopologyDataNotFoundException 
	 */
	@GetMapping(value = "/cards", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Find all Kpis cards", notes = "returns a a list of mw links")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
	public OverallStatsRepresentation getTopologyData() throws TopologyDataNotFoundException {
		return overallStatsService.findKpiByLastUpdated();
	}
	
}
