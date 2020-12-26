package com.axilog.cov.dto.representation;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceDashRep {
	
	private String outlet_name;
	private String code_categorie;
	private Long total_quantities_inHand;

}
