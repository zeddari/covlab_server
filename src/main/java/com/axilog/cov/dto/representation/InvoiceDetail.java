package com.axilog.cov.dto.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceDetail {
	private String invoiceId;
	private Double paymentAmount;
	private String paymentDate;

	private String supervisorName;
	private String driverName;
	private String taskId;
	private String motif;
	private String reason;
}