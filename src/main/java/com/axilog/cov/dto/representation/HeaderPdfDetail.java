package com.axilog.cov.dto.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeaderPdfDetail {

	private String creationDate;
	private String DueDate;
	private String destination;
	private String orderNumber;
	private String vendor;
	private String contactPersonName;
	private String contactPersonEmail;
	private String contactPersonMobile;
}
