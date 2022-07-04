package com.axilog.cov.dto;



import com.axilog.cov.validator.ValidateStringEnum;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "UserTasksListQuery", description = "Model UserTasksListQuery")
@Data
public class UserTasksListQuery {

	private int page;
	
	private int size;
	
	private String group;
	
	private String sort;
	private String businessKey;
	private String context;
	private String applicationId;
	
	@ValidateStringEnum(acceptedValues = { "ASSIGNED", "UNASSIGNED", "ASSIGNED_TO_ME", "ALL" }, message = "must be in {acceptedValues}")
	private String assigned;
	
	@ValidateStringEnum(acceptedValues = { "SUSPEND", "UNSUSPEND" }, message = "must be in {acceptedValues}")
	private String suspend;
	
	//private String equipName;
	
	private String taskDefinitionKey;

}
