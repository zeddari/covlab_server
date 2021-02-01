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
public class OutletDetail {
	
		private Long outletId;

	    private String outletName;

	    private String outletRegion;
	    
	    private String outletParentRegion;

	    private String outletAdress;

	    private Double outletLat;

	    private Double outletLng;	 

}
