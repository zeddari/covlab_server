package com.axilog.cov.exception.enums;

public enum ExceptionWorkflowEnum {

	NO_PROCESS_WATING_FOR_MESSAGE_EVENT("ERR_WORKFLOW_01",
			"No process with the application id: %s is waiting for the event: %s"),
	NOT_FOUND_REQUEST_SERVICE("ERR_WORKFLOW_02", "Not found exception throw from request service when : %s"),
	NEXT_TASK_NOT_FOUND("ERR_WORKFLOW_03", "No adjudication available"),
	NEXT_TASK_NOT_AVAILABLE("ERR_WORKFLOW_04", "Task already processed"),
	PROCESS_NOT_FOUND("ERR_WORKFLOW_05", "The process %s not found by the business key %s"),
	DEAD_LETTER_JOB_NOT_FOUND("ERR_WORKFLOW_06", "Dead letter job %s not found"),
	BAD_REQUEST_NULL_BODY("ERR_WORKFLOW_08", "Bad Request, null body"),
	REQUEST_NOT_FOUND("ERR_REQUEST_01", "Request not found")
	;
	
	private String code;
	private String libelle;

	private ExceptionWorkflowEnum(String code, String libelle) {
		this.code = code;
		this.libelle = libelle;
	}

	public String getCode() {
		return code;
	}

	public String getLibelle() {
		return libelle;
	}

}
