package com.axilog.cov.web.rest.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axilog.cov.dto.topology.representation.MapRepresentation;
import com.axilog.cov.exception.MapDataNotFoundException;
import com.axilog.cov.exception.TopologyDataNotFoundException;
import com.axilog.cov.service.map.MapReaderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/map")
@Api(value = "Map Rest Api", description = "Operations pertaining to Map Apis")
public class MapController {

	@Autowired
	private MapReaderService mapReaderService;

	/**
	 * @return
	 * @throws TopologyDataNotFoundException 
	 * @throws AlarmNotFoundException 
	 */
	@GetMapping(value = "/data/all", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Find all the data to be shown in the map", notes = "returns a a list of mw links")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public MapRepresentation getTopologyData() throws MapDataNotFoundException, TopologyDataNotFoundException {
		return mapReaderService.getMapData();
	}

}
