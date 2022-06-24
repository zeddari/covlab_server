package com.axilog.cov.dto.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeadLetterJobRepresentation {
	private String id;
	private String businessKey;
	private String processInstanceId;
	private String processDefinitionName;
	private Integer processDefinitionVersion;
	private String processDefinitionId;
	private String exceptionMessage;
	private String activityId;
	private Date dueDate;
	private int retries;
	private String executionId;
}
