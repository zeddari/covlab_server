package com.axilog.cov.dto.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PurchaseOrderCommand {
	private String orderNo;
	private String status;
	private boolean emailToBeSent;
	private int approvalLevel;
}
