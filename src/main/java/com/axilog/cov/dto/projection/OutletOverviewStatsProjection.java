package com.axilog.cov.dto.projection;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;

public interface  OutletOverviewStatsProjection {
	
	public Double getAverageQuantitiesConsumed();
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getLastUpdatedAt();
	public Long getOutletId();
	public String getOutletName();
	public Double getTotalQuantitiesConsumed();
	public Double getTotalQuantitiesInHand();
	
	
	  
}
