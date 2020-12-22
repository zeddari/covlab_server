package com.axilog.cov.dto.topology.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Font {

	
	
	private String color;
	private Integer size;
	private String align;
}
