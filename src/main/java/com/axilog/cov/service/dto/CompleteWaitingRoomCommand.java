package com.axilog.cov.service.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompleteWaitingRoomCommand {

	private String comment;
	private String action;
	private String taskId;
	private Map<String, Object> variables;
	private List<String> userGroupList;
	private String userId;
}
