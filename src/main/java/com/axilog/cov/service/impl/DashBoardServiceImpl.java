package com.axilog.cov.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axilog.cov.dto.projection.DashInventoryComProjection;
import com.axilog.cov.dto.projection.DashInventoryDailyTrendProjection;
import com.axilog.cov.dto.projection.DashInventoryStockAllOutletProjection;
import com.axilog.cov.dto.projection.DashInventoryStockProjection;
import com.axilog.cov.dto.representation.AreaDataDet;
import com.axilog.cov.dto.representation.AreaDataDetail;
import com.axilog.cov.dto.representation.ChartDetail;
import com.axilog.cov.dto.representation.DashBoardRepresentation;
import com.axilog.cov.dto.representation.LineChartDetail;
import com.axilog.cov.dto.representation.LineChartDetailMine;
import com.axilog.cov.dto.representation.SeriesDetail;
import com.axilog.cov.repository.DashBoardRepository;
import com.axilog.cov.repository.PurchaseOrderRepository;
import com.axilog.cov.service.DashBoardService;
import com.axilog.cov.util.DateUtil;

@Service
@Transactional
public class DashBoardServiceImpl implements DashBoardService {



	@Autowired
	private DashBoardRepository dashBoardRepository;
	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;

	@Override
	public List<SeriesDetail> getKpiByInventoryCompo(String outlet) {

		List<DashInventoryComProjection> inventories = dashBoardRepository.getInventoryCompo(outlet);
		List<SeriesDetail> data = new ArrayList<>();
		if (inventories != null && !inventories.isEmpty()) {
			for (DashInventoryComProjection el : inventories) {
				data.add(SeriesDetail.builder().name(el.getStatus()).value(el.getValue()).build());
			}
			return data;
		}

		return data;
	}

	@Override
	public DashBoardRepresentation getKpiByRotStock() {
		return null;
	}

	@Override
	public List<ChartDetail> getKpiStockByOutlet(String outlet) {
		List<ChartDetail> chartDetails = new ArrayList<>();
		List<DashInventoryStockProjection> inventories = dashBoardRepository.getStockOutlet(outlet);
		List<SeriesDetail> series = new ArrayList<>();
		if (Optional.ofNullable(inventories).isPresent()) {
			inventories.forEach(inventory -> {
				series.add(SeriesDetail.builder()
						.name(inventory.getCategory())
						.value(inventory.getValue())
						.build());
			});
			
		}
		chartDetails.add(ChartDetail.builder()
				.name("Category")
				.series(series)
				.build());
		return chartDetails;
	}

	@Override
	public List<ChartDetail> getKpiByOutletCategory(String outlet, String category) {
		List<ChartDetail> chartDetails = new ArrayList<>();
		List<DashInventoryStockProjection> inventories = purchaseOrderRepository.getDeliveryOutletCategory(outlet, category);
		List<SeriesDetail> series = new ArrayList<>();
		String categoryName = null;
		if (Optional.ofNullable(inventories).isPresent()) {
			inventories.forEach(inventory -> {
				series.add(SeriesDetail.builder()
						.name(inventory.getProduct())
						.value(inventory.getValue())
						.build());
				
			});
			if (inventories.size() > 0)
				categoryName = inventories.get(0).getCategory();
		}
		chartDetails.add(ChartDetail.builder()
				.name(categoryName)
				.series(series)
				.build());
		return chartDetails;
		
		
	}

	@Override
	public List<ChartDetail> getKpiStockForAllOutlet() {
		List<ChartDetail> chartDetails = new ArrayList<>();
		List<DashInventoryStockAllOutletProjection> inventories = dashBoardRepository.getStockForAllOutlet();
		List<SeriesDetail> series = new ArrayList<>();
		if (Optional.ofNullable(inventories).isPresent()) {
			inventories.forEach(inventory -> {
				series.add(SeriesDetail.builder()
						.name(inventory.getOutletName())
						.value(inventory.getValue())
						.build());
			});
			
		}
		chartDetails.add(ChartDetail.builder()
				.name("Location")
				.series(series)
				.build());
		return chartDetails;
	}

	@Override
	public List<ChartDetail> getAvgStockDaysByOutletCategory(String outlet, String category) {
		List<ChartDetail> chartDetails = new ArrayList<>();
		List<DashInventoryStockProjection> inventories = dashBoardRepository.getAvgStockDaysOutletCategory(outlet, category);
		List<SeriesDetail> series = new ArrayList<>();
		if (Optional.ofNullable(inventories).isPresent()) {
			inventories.forEach(inventory -> {
				series.add(SeriesDetail.builder()
						.name(inventory.getProduct())
						.value(inventory.getValue())
						.build());
			});
		}
		chartDetails.add(ChartDetail.builder()
				.name(category)
				.series(series)
				.build());
		return chartDetails;
	}

	@Override
	public LineChartDetail getVaccinationDailyTrend(String outlet) {
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.SIMPLE_DATE_PATTERN);
		List<DashInventoryDailyTrendProjection> dailyTrendData = dashBoardRepository.getKpiDailyTrend(outlet);
		List<DashInventoryDailyTrendProjection> dailyConsumedData = dashBoardRepository.getKpiDailyConsumed(outlet);
		
		AreaDataDetail areaDataDetailTrend = AreaDataDetail.builder().build();
		AreaDataDetail areaDataDetailConsumed = AreaDataDetail.builder().build();
		List<AreaDataDetail> areaDataDetails = new ArrayList<>();
		
		List<String> areaChartLabelsTrend = new ArrayList<>();
		List<String> areaChartLabelsConsumed = new ArrayList<>();
		
		if(dailyTrendData != null) {
			areaDataDetailTrend.setLabel("Daily Trend");
			List<Long> datasTrend = new ArrayList<>();
			dailyTrendData.forEach(trend -> {
				areaChartLabelsTrend.add(sdf.format(trend.getDate()));
				datasTrend.add(trend.getValue());
			});
			areaDataDetailTrend.setData(datasTrend);
		}
		areaDataDetails.add(areaDataDetailTrend);
	
		if(dailyConsumedData != null) {
			areaDataDetailConsumed.setLabel("Daily Consumed");
			List<Long> datasConsumed = new ArrayList<>();
			dailyConsumedData.forEach(consumed -> {
				areaChartLabelsConsumed.add(sdf.format(consumed.getDate()));
				datasConsumed.add(consumed.getValue());
			});
			areaDataDetailConsumed.setData(datasConsumed);	
		}
		areaDataDetails.add(areaDataDetailConsumed);
	
		if(dailyTrendData != null)
			return LineChartDetail.builder().areaChartLabels(areaChartLabelsConsumed).areaDataDetail(areaDataDetails).build();

		if(dailyTrendData != null)
			return LineChartDetail.builder().areaChartLabels(areaChartLabelsConsumed).areaDataDetail(areaDataDetails).build();

		//if (dailyTrendData == null && dailyConsumedData == null) 
			return LineChartDetail.builder().build();
	}
	
	
	@Override
	public LineChartDetailMine getVaccinationDailyTrendMine(String outlet) {
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.SIMPLE_DATE_PATTERN);
		List<DashInventoryDailyTrendProjection> dailyTrendData = dashBoardRepository.getKpiDailyTrend(outlet);
		List<DashInventoryDailyTrendProjection> dailyConsumedData = dashBoardRepository.getKpiDailyConsumed(outlet);
		
		List<AreaDataDet> areaDataDetailsTrend = new ArrayList<AreaDataDet>();
		List<AreaDataDet> areaDataDetailsConsum = new ArrayList<AreaDataDet>();
		
		LineChartDetailMine chartData = LineChartDetailMine.builder().build();
		
		if(dailyTrendData != null) {
			dailyTrendData.forEach(trend -> {
				areaDataDetailsTrend.add(AreaDataDet.builder().label(sdf.format(trend.getDate())).y(trend.getValue()).build());
			});
			
			chartData.setTrend(areaDataDetailsTrend);
		}

		if(dailyConsumedData != null) {
			dailyConsumedData.forEach(consumed -> {
				areaDataDetailsConsum.add(AreaDataDet.builder().label(sdf.format(consumed.getDate())).y(consumed.getValue()).build());
			});
			
			chartData.setConsumed(areaDataDetailsConsum);
		}
	
		return chartData;
}

}
