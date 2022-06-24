package com.axilog.cov.web.rest.activity;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axilog.cov.aop.logging.annotation.ExcludeLog;
import com.axilog.cov.constant.WorkflowVariables;
import com.axilog.cov.domain.Request;
import com.axilog.cov.domain.Tasks;
import com.axilog.cov.dto.TaskDto;
import com.axilog.cov.dto.UserTasksListQuery;
import com.axilog.cov.exception.RequestNotFoundException;
import com.axilog.cov.exception.base.BadRequestException;
import com.axilog.cov.repository.RequestRepository;
import com.axilog.cov.repository.TasksRepository;
import com.axilog.cov.security.jwt.JWTParser;
import com.axilog.cov.security.jwt.TokenProvider;
import com.axilog.cov.service.TaskService;
import com.axilog.cov.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * REST controller for managing {@link io.github.jhipster.sample.domain.activity.Tasks}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TasksResource {

    private final Logger log = LoggerFactory.getLogger(TasksResource.class);

    private static final String ENTITY_NAME = "tasks";
    private static final String JWT_HEADER = "Authorization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TasksRepository tasksRepository;
    
	@Autowired
	private TokenProvider tokenProvider;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private RequestRepository requestRepository;

    public TasksResource(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    /**
     * {@code PUT  /tasks} : Updates an existing tasks.
     *
     * @param tasks the tasks to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tasks,
     * or with status {@code 400 (Bad Request)} if the tasks is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tasks couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tasks")
    public ResponseEntity<Tasks> updateTasks(@RequestBody Tasks tasks) throws URISyntaxException {
        log.debug("REST request to update Tasks : {}", tasks);
        if (tasks.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Tasks result = tasksRepository.save(tasks);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tasks.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tasks} : get all the tasks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tasks in body.
     */
    @PostMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @PostMapping("/tasks/claim/{taskId}")
	@ApiOperation(value = "Claim task")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", dataType = "string", paramType = "header")
	public void claim(@RequestHeader(name = JWT_HEADER) String jwtHeader, @PathVariable String taskId)
			throws BadRequestException {
		JWTParser jwtParser = new JWTParser(jwtHeader, tokenProvider.getKey());
		jwtParser.checkAuthorizedGroup();
		String userId = jwtParser.getUserId();
		
		taskService.claimTask(taskId, userId);
	}

	@PostMapping("/tasks/complete/{taskId}")
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
		//update status
		String requestId = (String)variables.get(WorkflowVariables.REQUEST_ID);
		Request request = requestRepository.findByRequestId(requestId);
		
		
	}

	@PostMapping("/tasks/unclaim/{taskId}")
	@ApiOperation(value = "UnClaim task")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", dataType = "string", paramType = "header")
	public void unclaim(@RequestHeader(name = JWT_HEADER) String jwtHeader, @PathVariable String taskId)
			throws BadRequestException {
		JWTParser jwtParser = new JWTParser(jwtHeader, tokenProvider.getKey());
		String userId = jwtParser.getUserId();
		taskService.unclaimTask(taskId, userId);
	}
}
