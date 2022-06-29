/**
 * 
 */
package com.axilog.cov.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author y.nadir
 *
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WaitingRoomTaskCompletionCommand {

	private String comment;
	private String action;
}
