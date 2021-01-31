package com.axilog.cov.web.rest.toplogy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axilog.cov.dto.topology.representation.NodeIdListRepresentation;
import com.axilog.cov.dto.topology.representation.TopologyRepresentation;
import com.axilog.cov.exception.TopologyDataNotFoundException;
import com.axilog.cov.service.topology.TopologyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/topology")
@Api(value = "Topology Rest Api", description = "Operations pertaining to Topology Apis")
public class TopologyController {

	@Autowired
	private TopologyService topologyReaderService;

	/**
	 * @return
	 * @throws TopologyDataNotFoundException 
	 */
	@GetMapping(value = "/data/all", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Find all mwlinks", notes = "returns a a list of mw links")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
	public TopologyRepresentation getTopologyData() throws TopologyDataNotFoundException {
		return topologyReaderService.buildTopologyData();
	}
	
	/**
	 * @return
	 * @throws TopologyDataNotFoundException 
	 */
	/*
	@GetMapping(value = "/data/all/{statusOrTemperature}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Find all mwlinks", notes = "returns a a list of mw links")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
	public TopologyRepresentation getTopologyDataFroMap(@PathVariable(name="statusOrTemperature", required=true) String statusOrTemperature) throws TopologyDataNotFoundException {
		return topologyReaderService.buildTopologyDataWithParam(statusOrTemperature);
	}
	*/
	
	@GetMapping(value = "/nodes/id/list", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Find all nodes id", notes = "returns a a list of distinc tnode id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public List<NodeIdListRepresentation> getDistinctNodeId() throws TopologyDataNotFoundException {
		return topologyReaderService.getDistinctNodeId();
	}
	
	
	

}
