package com.axilog.cov.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum PurchaseStatusEnum {

	CREATED(1l, "CREATED"),
	PENDING_AP1(2l, "PENDING_AP1"),
	PENDING_AP2(3l, "PENDING_AP2"),
	SENT_TO_NUPCO(3l, "SENT_TO_NUPCO"),
	CLOSED(4l, "CLOSED");
		
		private Long code;
		private String label;
	}