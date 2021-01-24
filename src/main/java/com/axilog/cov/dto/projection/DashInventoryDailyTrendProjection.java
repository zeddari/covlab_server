package com.axilog.cov.dto.projection;

import java.util.Date;

public interface  DashInventoryDailyTrendProjection {
	
	public Date getDate();
	public String getOutletName();
	public Long getValue();

}
