/**
 * 
 */
package com.axilog.cov.service.dto;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author oussama
 *
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TasksCompletionCommand {
	
	@NotNull
	@NotEmpty
	List<String> tasksId;
	Map<String, Object> variables;
}
