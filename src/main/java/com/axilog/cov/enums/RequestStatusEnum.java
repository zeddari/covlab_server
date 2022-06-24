package com.axilog.cov.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum RequestStatusEnum {
	CREATED(1l, "CREATED"),
	ACQUIRED(2l, "ACQUIRED"),
	CONSTRUCTED(2l, "CONSTRUCTED"),
	INTEGRATED(2l, "INTEGRATED"),
	LIVE(2l, "LIVE"),
	CANCELED(2l, "CANCELED"),
	REJECTED(2l, "REJECTED");
	
	private Long code;
	private String label;
}
