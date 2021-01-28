package com.axilog.cov.dto.projection;

import java.util.Date;

public interface  OutletOverviewProjection {
	
	public Long getCurrentBalance();
	public Double getDeliveryOnTimeInFull();
	public Date   getLastUpdatedAt();
	public String getOutletName();
	public Double getOverallOutletPerformanceScore();
	public Double getStockoutRatio();
	public Long getTotalVaccinesConsumed();
    public Long getTotalVaccinesReceivedAtNupco();
    public Long getTotalVaccinesReceivedAtOutlets();
    public Double getWarehouseFillingRate();
    public Double getWastageVaccines();
    
    
}
