package com.axilog.cov.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class ApprovalConfigDoesNotExistException extends AbstractThrowableProblem {
    private static final long serialVersionUID = 1L;

    public ApprovalConfigDoesNotExistException() {
        super(ErrorConstants.INVALID_APPROVAL_CONFIG, "Approval Config does not exist", Status.BAD_REQUEST);
    }
}
