package com.axilog.cov.web.rest.activity;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axilog.cov.aop.logging.annotation.ExcludeLog;
import com.axilog.cov.dto.TaskDto;
import com.axilog.cov.dto.UserTasksListQuery;
import com.axilog.cov.exception.RequestNotFoundException;
import com.axilog.cov.exception.base.BadRequestException;
import com.axilog.cov.security.jwt.JWTParser;
import com.axilog.cov.security.jwt.TokenProvider;
import com.axilog.cov.service.activity.api.WorflowManagementService;
import com.axilog.cov.service.impl.TaskServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/ui/tasks")
@Api(tags = "Ui")
public class TaskController {

	private static final String JWT_HEADER = "Authorization";

	@Autowired
	private TaskServiceImpl taskService;

	@Autowired
	private WorflowManagementService worflowManagementService;
	
	@Autowired
	private TokenProvider tokenProvider;

	@GetMapping(value = "/next", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get next task", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful"),
			@ApiResponse(code = 404, message = "Next task not found") })
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", dataType = "string", paramType = "header")
	public TaskDto next(@RequestHeader(name = JWT_HEADER) String jwtHeader) throws Exception {
		JWTParser jwtParser = new JWTParser(jwtHeader, tokenProvider.getKey());
		String userId = jwtParser.getUserId();
		String adjGroup = jwtParser.checkAndGetAuthorizedGroup();

		return taskService.nextTask(userId, adjGroup);
	}

	@PostMapping("/claim/{taskId}")
	@ApiOperation(value = "Claim task")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", dataType = "string", paramType = "header")
	public void claim(@RequestHeader(name = JWT_HEADER) String jwtHeader, @PathVariable String taskId)
			throws BadRequestException {
		JWTParser jwtParser = new JWTParser(jwtHeader, tokenProvider.getKey());
		jwtParser.checkAuthorizedGroup();
		String userId = jwtParser.getUserId();

		taskService.claimTask(taskId, userId);
	}

	@PostMapping("/complete/{taskId}")
	@ApiOperation(value = "Complete task")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", dataType = "string", paramType = "header")
	@ExcludeLog
	public void complete(@RequestHeader(name = JWT_HEADER) String jwtHeader, @PathVariable String taskId,
			@ApiParam(value = "{ applicantRejected: boolean, applicationId: String }") @RequestBody Map<String, Object> variables)
			throws BadRequestException, RequestNotFoundException {
		JWTParser jwtParser = new JWTParser(jwtHeader, tokenProvider.getKey());
		jwtParser.checkAuthorizedGroup();
		String userId = jwtParser.getUserId();
		List<String> groups = jwtParser.getGroups();
		taskService.completeTask(taskId, userId, variables, groups);
	}

	@PostMapping("/unclaim/{taskId}")
	@ApiOperation(value = "UnClaim task")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", dataType = "string", paramType = "header")
	public void unclaim(@RequestHeader(name = JWT_HEADER) String jwtHeader, @PathVariable String taskId)
			throws BadRequestException {
		JWTParser jwtParser = new JWTParser(jwtHeader, tokenProvider.getKey());
		String userId = jwtParser.getUserId();
		taskService.unclaimTask(taskId, userId);
	}

	@GetMapping("/count")
	@ApiOperation(value = "Tasks Count")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", dataType = "string", paramType = "header")
	public long count(@RequestHeader(name = JWT_HEADER) String jwtHeader) {
		JWTParser jwtParser = new JWTParser(jwtHeader, tokenProvider.getKey());
		jwtParser.checkAuthorizedGroup();
		String groupId = jwtParser.getGroups().iterator().next();// TODO Depends on How many groups the orchestrator
		// manages ?
		return taskService.count(groupId);
	}

	@GetMapping(value = "/next/{applicationId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get next task by applicationId", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful"),
			@ApiResponse(code = 404, message = "Next task by applicationId not found") })
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", dataType = "string", paramType = "header")
	@ExcludeLog
	public TaskDto nextByApplicationId(@RequestHeader(name = JWT_HEADER) String jwtHeader,
			@PathVariable String applicationId) throws Exception {
		JWTParser jwtParser = new JWTParser(jwtHeader, tokenProvider.getKey());
		String userId = jwtParser.getUserId();
		String adjGroup = jwtParser.checkAndGetAuthorizedGroup();
		return taskService.nextTaskByApplicatioId(userId, adjGroup, applicationId);
	}

	@PostMapping(value = "/user/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get user tasks list", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful") })
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", dataType = "string", paramType = "header")
	public List<TaskDto> getUserTasksList(@RequestHeader(name = JWT_HEADER) String jwtHeader,
			@ApiParam(value = "request data to fill workflow variables", required = true) @Valid @RequestBody UserTasksListQuery userTasksListQuery)
			throws BadRequestException {
		JWTParser jwtParser = new JWTParser(jwtHeader, tokenProvider.getKey());
		String userId = jwtParser.getUserId();
		return taskService.getUserTaskListByBusinessKey(userId, userTasksListQuery);
	}
}
