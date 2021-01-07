package com.axilog.cov.dto.representation;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OverallStatsDetail {
	
	public Double averageQuantitiesConsumed;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date lastUpdatedAt;
	public Long outletId;
	public String outletName;
	public Double totalQuantitiesConsumed;
	public Double totalQuantitiesInHand;
	 

}
