package com.axilog.cov.web.rest.dashboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axilog.cov.dto.command.DeliveryChartCommand;
import com.axilog.cov.dto.representation.ChartDetail;
import com.axilog.cov.dto.representation.DashBoardRepresentation;
import com.axilog.cov.dto.representation.OverallStatsRepresentation;
import com.axilog.cov.dto.representation.SeriesDetail;
import com.axilog.cov.exception.TopologyDataNotFoundException;
import com.axilog.cov.service.DashBoardService;
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
	
	@Autowired
	private DashBoardService dashBoardService;
	 

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
	
	@GetMapping(value = "/inventoryCompo/{outlet}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Kpi Inventory Compo", notes = "returns a a list of mw links")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
	public List<SeriesDetail> getKpiInventoryCompo(@PathVariable(name="outlet", required=true) String outlet) throws TopologyDataNotFoundException {
		return dashBoardService.getKpiByInventoryCompo(outlet);
	}
	@GetMapping(value = "/rotStock", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Kpi Rot Stock", notes = "returns a a list of mw links")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
	public DashBoardRepresentation getKpiRotStock() throws TopologyDataNotFoundException {
		return dashBoardService.getKpiByRotStock();
	}
	@GetMapping(value = "/stock/{outlet}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Kpi stock per outlet", notes = "returns a a list of mw links")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
	public ChartDetail getKpiStockOutlet(@PathVariable(name="outlet", required=true) String outlet) throws TopologyDataNotFoundException {
		return dashBoardService.getKpiStockByOutlet(outlet);
	}
	
	@GetMapping(value = "/delivery/{outlet}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Kpi stock per outlet", notes = "returns a a list of mw links")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
	public ChartDetail getKpiStockOutletCategory(@PathVariable(name="outlet", required=true) String outlet,
			@RequestBody(required = true) DeliveryChartCommand chartCommand) throws TopologyDataNotFoundException {
		return dashBoardService.getKpiByOutletCategory(outlet, chartCommand.getCategory());
	}
	
}
