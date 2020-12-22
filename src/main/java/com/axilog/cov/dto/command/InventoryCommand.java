package com.axilog.cov.dto.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryCommand {
	private Long id;
	private Double quantitiesInHand;
	private Double quantitiesInTransit;
	private Double actualDailyConsumption;
}
