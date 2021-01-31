package com.axilog.cov.dto.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PoReportDetail {

    private String  item;
    private String  description;
    private String  uom;
    private Double  poOriginalQty;
    private Double  poReceivedQty;
    private Double  poBalanceQty;
    private Long 	etaOfDelivery;
    private String  outlet;
	
}
