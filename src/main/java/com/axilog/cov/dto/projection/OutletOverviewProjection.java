package com.axilog.cov.dto.projection;

import java.util.Date;

public interface  OutletOverviewProjection {
	
	public String getOutletName();
	public Double getCurrentBalance();
	public Double getDeliveryOnTimeInFull();
	public Date getLastUpdatedAt();
	public Double getOverallOutletPerformanceScore();
	public Double getStockoutRatio();
	public Double getTotalVaccinesConsumed();
    public Double getTotalVaccinesReceivedAtNupco();
    public Double getTotalVaccinesReceivedAtOutlets();
    public Double getWarehouseFillingRate();
    public Double getWastageVaccines();
    
    
}
