package com.axilog.cov.service;

import java.util.List;

import com.axilog.cov.domain.Category;
import com.axilog.cov.dto.representation.ChartDetail;
import com.axilog.cov.dto.representation.DashBoardRepresentation;
import com.axilog.cov.dto.representation.SeriesDetail;

/**
 * Service Interface for managing {@link Category}.
 */
public interface DashBoardService {
	
	public List<SeriesDetail>  getKpiByInventoryCompo(String outlet);
	public DashBoardRepresentation getKpiByRotStock();
	public List<ChartDetail> getKpiStockByOutlet(String outlet);
	public List<ChartDetail> getKpiStockForAllOutlet();
	public List<ChartDetail> getKpiByOutletCategory(String outlet, String category);
	public List<ChartDetail> getAvgStockDaysByOutletCategory(String outlet, String category);
	

}
