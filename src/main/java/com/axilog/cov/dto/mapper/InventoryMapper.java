package com.axilog.cov.dto.mapper;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.axilog.cov.domain.Inventory;
import com.axilog.cov.domain.Outlet;
import com.axilog.cov.domain.OverallStats;
import com.axilog.cov.domain.OverallStatsOutlet;
import com.axilog.cov.domain.Product;
import com.axilog.cov.dto.projection.OutletOverviewProjection;
import com.axilog.cov.dto.representation.HeaderPdfDetail;
import com.axilog.cov.dto.representation.InventoryDetail;
import com.axilog.cov.dto.representation.InventoryPdfDetail;
import com.axilog.cov.dto.representation.InventoryRepresentation;
import com.axilog.cov.dto.representation.OverallStatsRepresentation;
import com.axilog.cov.dto.representation.PoPdfDetail;
import com.axilog.cov.service.InventoryService;
import com.axilog.cov.util.DateUtil;

@Component
public class InventoryMapper {
	
	@Value("${vendor}")
	private String vendor;
	
	@Value("${contactPersonName}")
	private String contactPersonName;
	
	@Value("${contactPersonPhone}")
	private String contactPersonPhone;
	
	@Value("${contactPersonEmail}")
	private String contactPersonEmail;
	
	@Value("${poThreesholdCapacity}")
	private Integer poThreesholdCapacity;
	
	@Value("${poDueDateOffset}")
	private Integer poDueDateOffset;
	
	@Autowired
	private InventoryService inventoryService;
	/**
	 * @param inventory
	 * @return
	 */
	public InventoryDetail toInventoryDetail(Inventory inventory) {
		DecimalFormat formatter = (DecimalFormat)NumberFormat.getNumberInstance(Locale.US);
		formatter.applyPattern("##.#");
		return InventoryDetail.builder()
				.inventoryId(inventory.getId())
				.itemCode(inventory.getProduct().getProductCode())
				.description(inventory.getProduct().getDescription())
				.currentBalance(inventory.getCurrent_balance())
				.receivedQuantity(inventory.getReceivedQty())
				.quantitiesInTransit(inventory.getQuantitiesInTransit())
				.uom(inventory.getUom())
				.consumedQty(inventory.getConsumedQty())
				.actualAvgConsumption(inventory.getActualAvgConsumption())
				.reorderLevel(inventory.getReOrderLevel())
				.suggestedQuantity(inventory.getSuggestedQuantity())
				.expectedCoveringDay(inventory.getCapacity())
				.lastUpdatedAt(inventory.getLastUpdatedAt())
				.status(inventory.getStatus())
				.region(inventory.getOutlet().getOutletRegion())
				.outletName(inventory.getOutlet().getOutletName())
				.outletType(inventory.getOutlet().getOutletName())
				.outletAddress(inventory.getOutlet().getOutletAdress())
				.outletLat(inventory.getOutlet().getOutletLat())
				.outletLng(inventory.getOutlet().getOutletLng())
				.category(inventory.getProduct().getCategory().getCategoryCode())
				.temperature(formatter.format(inventory.getProduct().getDeviceOverviewStats() != null ? inventory.getProduct().getDeviceOverviewStats().getTemperature() : 0))
				.nupcoCode(inventory.getProduct().getProductCode())
				.build();
	}
	
	/**
	 * @param inventories
	 * @return
	 */
	public InventoryRepresentation toInventoryRepresentation(List<Inventory> inventories) {
		if (inventories == null) return InventoryRepresentation.builder().build();
		InventoryRepresentation inventoryRepresentation = InventoryRepresentation.builder().build();
		inventoryRepresentation.setInventoryData(new ArrayList<>());
		inventories.forEach(inventory -> {
			inventoryRepresentation.getInventoryData().add(toInventoryDetail(inventory));
		});
		return inventoryRepresentation;
	}
	
	/**
	 * @param overallStats
	 * @return
	 */								  
	public OverallStatsRepresentation toOverallStatsRepres(OverallStats overallStats) {
		return OverallStatsRepresentation.builder()
				.deliveryOnTimeInFull(overallStats.getDeliveryOnTimeInFull())
				.lastUpdatedAt(overallStats.getLastUpdatedAt())
				.overallOutletPerformanceScore(overallStats.getOverallOutletPerformanceScore())
				.stockoutRatio(overallStats.getStockoutRatio())
				.totalVaccinesConsumed(overallStats.getTotalVaccinesConsumed())
				.totalVaccinesReceivedAtNupco(overallStats.getTotalVaccinesReceivedAtNupco())
				.totalVaccinesReceivedAtOutlets(overallStats.getTotalVaccinesReceivedAtOutlets())
				.warehouseFillingRate(overallStats.getWarehouseFillingRate())
				.wastageVaccines(overallStats.getWastageVaccines())
				.currentBalance(overallStats.getCurrentBalance())
				.build();
	}
	
	public OverallStatsRepresentation toOverallStatsRepres(OverallStatsOutlet overallStats) {
		return OverallStatsRepresentation.builder()
				.deliveryOnTimeInFull(overallStats.getDeliveryOnTimeInFull())
				.lastUpdatedAt(overallStats.getLastUpdatedAt())
				.overallOutletPerformanceScore(overallStats.getOverallOutletPerformanceScore())
				.stockoutRatio(overallStats.getStockoutRatio())
				.totalVaccinesConsumed(overallStats.getTotalVaccinesConsumed())
				.totalVaccinesReceivedAtNupco(overallStats.getTotalVaccinesReceivedAtNupco())
				.totalVaccinesReceivedAtOutlets(overallStats.getTotalVaccinesReceivedAtOutlets())
				.warehouseFillingRate(overallStats.getWarehouseFillingRate())
				.wastageVaccines(overallStats.getWastageVaccines())
				.currentBalance(overallStats.getCurrentBalance())
				.build();
	}
	
	
	public OverallStatsRepresentation toOverallStatsRepresOutlet(OutletOverviewProjection overallStats) {
		return OverallStatsRepresentation.builder()
				.deliveryOnTimeInFull(overallStats.getDeliveryOnTimeInFull())
				.lastUpdatedAt(overallStats.getLastUpdatedAt())
				.overallOutletPerformanceScore(overallStats.getOverallOutletPerformanceScore())
				.stockoutRatio(overallStats.getStockoutRatio())
				.totalVaccinesConsumed(overallStats.getTotalVaccinesConsumed())
				.totalVaccinesReceivedAtNupco(overallStats.getTotalVaccinesReceivedAtNupco())
				.totalVaccinesReceivedAtOutlets(overallStats.getTotalVaccinesReceivedAtOutlets())
				.warehouseFillingRate(overallStats.getWarehouseFillingRate())
				.wastageVaccines(overallStats.getWastageVaccines())
				.currentBalance(overallStats.getCurrentBalance())
				.build();
	}
	
	/**
	 * @param inventory
	 * @return
	 */
	@Transactional
	public InventoryPdfDetail toPdfDetail(Inventory inventory) {
		Double desiredQty = (poThreesholdCapacity * inventory.getActualAvgConsumption()) - (inventory.getCurrent_balance() + inventory.getQuantitiesInTransit());
		inventory.setQuantitiesInTransit(desiredQty);
		inventoryService.save(inventory);
		return InventoryPdfDetail.builder()
				.code(inventory.getProduct().getProductCode())
				.description(inventory.getProduct().getDescription())
				.category(inventory.getProduct().getCategory() != null ? inventory.getProduct().getCategory().getCategoryDescription() : "")
				.uom(inventory.getUom())
				.quantity(desiredQty)
				.build();
	}
	
	/**
	 * @param inventories
	 * @return
	 */
	public PoPdfDetail toPdfListDetail(List<Inventory> inventories, List<Product> productsToBeInPo, Outlet outlet, Long poNumber) {
		List<InventoryPdfDetail> inventoryPdfDetails = new ArrayList<>();
		if (inventories == null) return PoPdfDetail.builder().build();
		inventories.forEach(inv -> {
			if (productsToBeInPo.contains(inv.getProduct())) {
				inventoryPdfDetails.add(toPdfDetail(inv));
			}
			
		});
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.MOI_DATE_TIME_ENCODING);
		String creationDate =  sdf.format(DateUtil.now()); 
		Date dateTenDays = DateUtil.addDay(DateUtil.now(), poDueDateOffset);
		String dueDate = sdf.format(dateTenDays); 
		HeaderPdfDetail headerPdfDetail = HeaderPdfDetail.builder().destination(outlet.getOutletName())
				.creationDate(creationDate)
				.DueDate(dueDate)
				.vendor(vendor)
				.contactPersonEmail(contactPersonEmail)
				.contactPersonMobile(contactPersonPhone)
				.contactPersonName(contactPersonName)
				.orderNumber(Long.toString(poNumber))
				.build();
		return PoPdfDetail.builder().listDetails(inventoryPdfDetails).headerPdfDetail(headerPdfDetail).outlet(outlet.getOutletName()).build();
	}
	
}
