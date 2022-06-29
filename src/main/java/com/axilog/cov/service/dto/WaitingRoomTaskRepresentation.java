package com.axilog.cov.service.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WaitingRoomTaskRepresentation {

	private String taskId;
	private String taskName;
	private Date creationDate;
	private String context;
	private String event;
	private String externalId;
	private String error;
	private String assignee;
	private List<String> actions;
	private String requestId;
	private String category;
	private String applicationId;
}
