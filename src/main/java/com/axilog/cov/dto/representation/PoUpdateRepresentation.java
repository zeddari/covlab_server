package com.axilog.cov.dto.representation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PoUpdateRepresentation {
	
	public Long id;
	public String code;
	public String category;
	public String uom;
	public Double quantity;
	 

}
