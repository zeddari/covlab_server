package com.axilog.cov.web.rest.activity.start;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.axilog.cov.dto.command.workflow.StartQuotationRequestProcessCommand;
import com.axilog.cov.exception.process.DuplicatedProcessSameBKException;
import com.axilog.cov.facade.api.workflow.WorkflowStartProcessFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;
import javassist.tools.web.BadHttpRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Api(tags = "Ui")
public class StartQuotationRequestProcessController {

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields();
	}

	@Autowired
	private WorkflowStartProcessFacade workflowStartProcessFacade;

	@PostMapping(path = "/api/ui/workflow/quotation/start")
	@ApiOperation(value = "start quotation workflow ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful"),
			@ApiResponse(code = 400, message = "Bad Request, Validation Errors, ...", response = BadHttpRequest.class),
			@ApiResponse(code = 500, message = "Internal server error", response = InternalError.class),
			@ApiResponse(code = 404, message = "Request Not Found", response = NotFoundException.class) })
	public void startQuotationRequestProcess(@RequestBody @Valid StartQuotationRequestProcessCommand StartQuotationRequestProcessCommand)
			throws DuplicatedProcessSameBKException {
		log.info("Starting Quotation Request process, applicationId {}", StartQuotationRequestProcessCommand.getApplicationId());
		workflowStartProcessFacade.startQuotationRequestProcess(StartQuotationRequestProcessCommand);
	}
}
