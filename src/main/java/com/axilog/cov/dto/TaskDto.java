package com.axilog.cov.dto;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDto {

	
	private Long id;
    private String taskId;
    private String taskName;
    private int priority;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	private Date dueDate;
    private String processInstanceId;
    private String assignee;
    private String requestId;
    private String equipName;
	private Map<String, Object> processVariables;


}
