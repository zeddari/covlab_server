package com.axilog.cov.dto.representation;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoicePdfDetail {
	private String mobileNumber;
	private String serviceType;
	private String customerName;
	private String customerEmail;
	private String product;
	private String header;
	private InvoiceDetail invoiceDetail ;
	


}
