package com.axilog.cov.dto.base;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractQuery {
	@Min(0)
	private int page;

	@Min(1)
	private int size;

	private String sort;

	public abstract String[] getSupportedSortKeys();

}
