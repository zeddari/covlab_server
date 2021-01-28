package com.axilog.cov.dto.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InventoryCommand {
	private Long id;
	private Double currentBalance;
	private Double quantitiesInTransit;
	private Double consumeQty;
}
