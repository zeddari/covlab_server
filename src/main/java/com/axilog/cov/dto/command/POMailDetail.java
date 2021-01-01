package com.axilog.cov.dto.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class POMailDetail {
	private Long poNumber;
	private String status;
	private Boolean emailToBeSent;
	private Integer approvalLevel;
}
