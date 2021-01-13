package com.axilog.cov.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axilog.cov.dto.projection.DashInventoryComProjection;
import com.axilog.cov.dto.projection.DashInventoryStockAllOutletProjection;
import com.axilog.cov.dto.projection.DashInventoryStockProjection;
import com.axilog.cov.dto.representation.ChartDetail;
import com.axilog.cov.dto.representation.DashBoardRepresentation;
import com.axilog.cov.dto.representation.SeriesDetail;
import com.axilog.cov.repository.DashBoardRepository;
import com.axilog.cov.repository.PurchaseOrderRepository;
import com.axilog.cov.service.DashBoardService;

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

}
