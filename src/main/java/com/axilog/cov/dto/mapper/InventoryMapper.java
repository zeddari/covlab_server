package com.axilog.cov.dto.mapper;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.axilog.cov.domain.Inventory;
import com.axilog.cov.domain.Outlet;
import com.axilog.cov.domain.OverallStats;
import com.axilog.cov.domain.OverallStatsOutlet;
import com.axilog.cov.domain.Product;
import com.axilog.cov.domain.PurchaseOrder;
import com.axilog.cov.domain.Substitute;
import com.axilog.cov.dto.projection.OutletOverviewProjection;
import com.axilog.cov.dto.representation.HeaderPdfDetail;
import com.axilog.cov.dto.representation.InventoryDetail;
import com.axilog.cov.dto.representation.InventoryPdfDetail;
import com.axilog.cov.dto.representation.InventoryRepresentation;
import com.axilog.cov.dto.representation.OutletDetail;
import com.axilog.cov.dto.representation.OutletRepresentation;
import com.axilog.cov.dto.representation.OverallStatsRepresentation;
import com.axilog.cov.dto.representation.PoPdfDetail;
import com.axilog.cov.service.InventoryService;
import com.axilog.cov.service.PurchaseOrderHistoryService;
import com.axilog.cov.service.PurchaseOrderService;
import com.axilog.cov.util.DateUtil;
import com.axilog.cov.util.JsonUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
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
	
	@Autowired
	private PurchaseOrderHistoryService purchaseOrderHistoryService;
	
	@Autowired
	private PurchaseOrderService purchaseOrderService;
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
				.currentBalance(inventory.getCurrent_balance() != null ? inventory.getCurrent_balance() : 0)
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
				.consumedUserQte(inventory.getConsumedUserQte())
				.receivedUserQte(inventory.getReceivedUserQte())
				.wastage(inventory.getWastage())
				.damage(inventory.getDamage())
				.sample(inventory.getSample())
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
		DecimalFormat formatter = (DecimalFormat)NumberFormat.getNumberInstance(Locale.US);
		formatter.applyPattern("##.##");
		return OverallStatsRepresentation.builder()
				.deliveryOnTimeInFull(overallStats.getDeliveryOnTimeInFull())
				.lastUpdatedAt(overallStats.getLastUpdatedAt())
				.overallOutletPerformanceScore(overallStats.getOverallOutletPerformanceScore())
				.stockoutRatio(Double.parseDouble(formatter.format(overallStats.getStockoutRatio())))
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
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 */
	@Transactional
	public InventoryPdfDetail toPdfDetail(Inventory inventory, List<Product> productsHavePo, Long poNumber, List<PurchaseOrder> purchaseOrders) throws JsonMappingException, JsonProcessingException {
		String productCode = inventory.getProduct().getProductCode();
		Inventory previousInventoryData = inventoryService.getPreviousCurrentBallenceData(Long.toString(inventory.getProduct().getId()), inventory.getOutlet().getId());
//		PurchaseOrderHistory purchaseOrderHistory = purchaseOrderHistoryService.findByOrderNo(Long.toString(poNumber));
//		PurchaseOrder purchaseOrder = purchaseOrderService.findByOrderNo(Long.toString(poNumber));
		Double nextBalance = inventory.getCurrent_balance();
		Double previousBalance = 0d;
		boolean hasHistory = false;
		Double desiredQty = 0d;
		if (productsHavePo.contains(inventory.getProduct())) {
			List<PurchaseOrder> ordersThisProduct = new ArrayList<>();
			for (PurchaseOrder  po : purchaseOrders) {
				if (po.getProducts() != null && po.getProducts().contains(inventory.getProduct())) {
					ordersThisProduct.add(po);
				}
			}
//			Optional<Date> maxPoCreatedAtOpt = ordersThisProduct.stream().map(PurchaseOrder :: getCreatedAt).max(Date::compareTo);
			PurchaseOrder maxPoOpt = Collections.max(ordersThisProduct, Comparator.comparing(PurchaseOrder :: getCreatedAt));
			if (maxPoOpt != null) {
				if (Optional.ofNullable(previousInventoryData).isPresent()) {
					if (maxPoOpt.getCreatedAt().compareTo(previousInventoryData.getLastUpdatedAt()) < 0) {
						hasHistory = true;
						previousBalance = previousInventoryData.getCurrent_balance();
						nextBalance = previousBalance - nextBalance;
						PoPdfDetail pdfDetailNew = JsonUtils.toJsonObject(maxPoOpt.getHotJson());
						Double oldItemQty = 0D;
						if (pdfDetailNew != null) {
							List<InventoryPdfDetail> details = pdfDetailNew.getListDetails().stream().filter(dt -> dt.getCode().equals(productCode)).collect(Collectors.toList());
							if (details != null && !details.isEmpty()) {
								oldItemQty = details.get(0).getQuantity();
							}
						}
						desiredQty = (poThreesholdCapacity * inventory.getActualAvgConsumption()) - (nextBalance + oldItemQty);
					}
					else {
						log.info("This item {}, in this outlet {} will not be included because no change has been done on its balance.", productCode, inventory.getOutlet().getOutletName());
					}
					
				}
			}
			else {
				desiredQty = (poThreesholdCapacity * inventory.getActualAvgConsumption()) - (nextBalance + inventory.getQuantitiesInTransit());
			}
			
		}
		else {
			desiredQty = (poThreesholdCapacity * inventory.getActualAvgConsumption()) - (nextBalance + inventory.getQuantitiesInTransit());
		}
		
		
		
//		if ((hasHistory && nextBalance != 0) || (!hasHistory && !productsHavePo.contains(inventory.getProduct()) )) {
//			desiredQty = (poThreesholdCapacity * inventory.getActualAvgConsumption()) - (nextBalance + inventory.getQuantitiesInTransit());
//		}
		
		//inventory.setQuantitiesInTransit(desiredQty);
		//inventoryService.save(inventory);
		return InventoryPdfDetail.builder()
				.code(inventory.getProduct().getProductCode())
				.description(inventory.getProduct().getDescription())
				.category(inventory.getProduct().getCategory() != null ? inventory.getProduct().getCategory().getCategoryDescription() : "")
				.uom(inventory.getUom())
				.quantity(desiredQty)
				.subsCodesCol("")
				.subsCodes(inventory.getProduct().getSubstitutes().stream().map(Substitute::getSubstituteCode).collect(Collectors.toList()))
				.subsMapCategories(inventory.getProduct().getSubstitutes().stream().collect(Collectors.toMap(Substitute::getSubstituteCode, Substitute::getSubstituteCategory)))
				.subsMapDescriptions(inventory.getProduct().getSubstitutes().stream().collect(Collectors.toMap(Substitute::getSubstituteCode, Substitute::getSubstituteDescription)))
				.balance(inventory.getCurrent_balance())
				.build();
	}
	
	/**
	 * @param inventories
	 * @return
	 */
	public PoPdfDetail toPdfListDetail(List<Inventory> inventories, List<Product> productsToBeInPo, Outlet outlet, Long poNumber, List<Product> productsHavePo, List<PurchaseOrder> purchaseOrders) {
		List<InventoryPdfDetail> inventoryPdfDetails = new ArrayList<>();
		if (inventories == null) return PoPdfDetail.builder().build();
		inventories.forEach(inv -> {
			if (productsToBeInPo.contains(inv.getProduct())) {
				InventoryPdfDetail invDetail;
				try {
					invDetail = toPdfDetail(inv, productsHavePo, poNumber, purchaseOrders);
					if (invDetail.getQuantity() > 0)
						inventoryPdfDetails.add(invDetail);
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
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
	
	public OutletDetail toOutletDetail(Outlet outlet) {
		return OutletDetail.builder()
				.outletId(outlet.getId())
			    .outletName(outlet.getOutletName())
			    .outletRegion(outlet.getOutletRegion())
			    .outletParentRegion(outlet.getOutletParentRegion())
			    .outletAdress(outlet.getOutletAdress())
			    .outletLat(outlet.getOutletLat())
			    .outletLng(outlet.getOutletLng())
				.build();
	}
	
	public OutletRepresentation toOutletRepresentation(List<Outlet> outlets) {
		if (outlets == null) return OutletRepresentation.builder().build();
		OutletRepresentation outletRepresentation = OutletRepresentation.builder().build();
		outletRepresentation.setOutletData(new ArrayList<>());
		outlets.forEach(outlet -> {
			outletRepresentation.getOutletData().add(toOutletDetail(outlet));
		});
		return outletRepresentation;
	}
	
	
}
