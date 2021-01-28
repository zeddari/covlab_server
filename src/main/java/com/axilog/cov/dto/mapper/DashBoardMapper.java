package com.axilog.cov.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.axilog.cov.domain.Inventory;
import com.axilog.cov.domain.Product;
import com.axilog.cov.dto.representation.ChartDetail;
import com.axilog.cov.dto.representation.DashBoardRepresentation;
import com.axilog.cov.dto.representation.InventoryRepresentation;
import com.axilog.cov.dto.representation.SeriesDetail;

@Component
public class DashBoardMapper {
	
	public ChartDetail toDashBoardDetail(Inventory inventory) {
		List<SeriesDetail> series = new ArrayList<>();
		return ChartDetail.builder()
				.series(series)
				.build();
	}
	public DashBoardRepresentation toDashBoardRepresentation(List<Inventory> inventories) {
		if (inventories == null) return DashBoardRepresentation.builder().build();
		DashBoardRepresentation dashBoardRepresentation = DashBoardRepresentation.builder().build();
		dashBoardRepresentation.setDashBoardDetails((new ArrayList<>()));
		inventories.forEach(inventory -> {
			dashBoardRepresentation.getDashBoardDetails().add(toDashBoardDetail(inventory));
		});
		return dashBoardRepresentation;
	}
	
	
}
