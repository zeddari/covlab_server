package com.axilog.cov.dto.command;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InventoryHistoryCommand {
	
	@NotNull
	private Date startDate;
	
	@NotNull
	private Date endDate;
}
