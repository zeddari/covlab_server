package com.axilog.cov.dto.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceRequest {

	private String driverName;
	private String motif;
	private Double paymentAmount;
	private String reason;
	private String supervisorName;
	private String taskId;
	private String requestQuotationId;
	private String invoiceId;
}
