package com.axilog.cov.service.impl.topology;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.axilog.cov.constant.TopologyConstant;
import com.axilog.cov.domain.Category;
import com.axilog.cov.domain.DeviceOverviewStats;
import com.axilog.cov.domain.Inventory;
import com.axilog.cov.domain.Outlet;
import com.axilog.cov.domain.Product;
import com.axilog.cov.dto.mapper.InventoryMapper;
import com.axilog.cov.dto.mapper.TopologyMapper;
import com.axilog.cov.dto.projection.OutletOverviewProjection;
import com.axilog.cov.dto.topology.MapDataBuilder;
import com.axilog.cov.dto.topology.representation.EdgeRepresentation;
import com.axilog.cov.dto.topology.representation.KpiTable;
import com.axilog.cov.dto.topology.representation.NodeIdListRepresentation;
import com.axilog.cov.dto.topology.representation.NodeRepresentation;
import com.axilog.cov.dto.topology.representation.TopologyRepresentation;
import com.axilog.cov.exception.TopologyDataNotFoundException;
import com.axilog.cov.repository.CategoryRepository;
import com.axilog.cov.repository.InventoryRepository;
import com.axilog.cov.repository.OverallStatsRepository;
import com.axilog.cov.repository.ProductRepository;
import com.axilog.cov.service.DeviceOverviewStatsService;
import com.axilog.cov.service.OutletService;
import com.axilog.cov.service.topology.TopologyService;

@Service
public class TopologyServiceImpl implements TopologyService {

	@Autowired
	private InventoryRepository inventoryRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OutletService outletService;

	@Autowired
	private DeviceOverviewStatsService deviceOverviewStatsService;

	@Autowired
	private InventoryMapper inventoryMapper;

	@Value("${outlet.kpi.temperatureMin}")
	private Double outletKpiTemperatureMin;

	@Value("${outlet.kpi.temperatureMax}")
	private Double outletKpiTemperatureMax;

	@Value("${outlet.kpi.humidityMin}")
	private Double outletKpiHumidityMin;

	@Value("${outlet.kpi.humidityMax}")
	private Double outletKpiHumidityMax;

	@Value("${outlet.kpi.pressureMin}")
	private Double outletKpiPressureMin;

	@Value("${outlet.kpi.pressureMax}")
	private Double outletKpiPressureMax;

	private String geoData;

	@Autowired
	private OverallStatsRepository overallStatsRepository;

	@Override
	public TopologyRepresentation buildTopologyData() throws TopologyDataNotFoundException {
		/** get all network elements */
		List<Outlet> outlets = outletService.findAll();
		List<DeviceOverviewStats> deviceOverviewStats = deviceOverviewStatsService.findAll();
		List<NodeRepresentation> nodes = new ArrayList<NodeRepresentation>();
		List<EdgeRepresentation> edges = new ArrayList<>();
		List<OutletOverviewProjection> overallStatsOutlet = overallStatsRepository.getKpiOutletCustomQuery("all");
		int geoDataExist = 0;
		geoData = MapDataBuilder.headerGeo();
		for (Outlet outlet : outlets) {

			/** get a list of devices */
			List<DeviceOverviewStats> deviceOverviewStatsFiltered = deviceOverviewStats.stream()
					.filter(device -> device.getOutlet().getOutletName().equals(outlets)).collect(Collectors.toList());

			/** calculate the health */
			String outletHealth = getOutletHealth(deviceOverviewStatsFiltered);
			NodeRepresentation node = TopologyMapper.toNodeRepresentation(outlet, outletHealth);
			String status = "";
			double balance = 0;
			double consumedQty = 0;
			String temperature = "";
			/** get details of outlet inventory */
			Inventory inventory = Inventory.builder().isLastInstance(true).outlet(outlet).product(
					Product.builder().category(Category.builder().categoryCode("COVID VACCINE").build()).build())
					.build();
			Example<Inventory> exampleInventory = Example.of(inventory);
			// Optional<Inventory> optInventory =
			// inventoryRepository.findOne(exampleInventory);
			Optional<Inventory> optInventory = inventoryRepository.findByOutletOutletId(outlet.getOutletId()).stream()
					.findFirst();
			if (optInventory.isPresent())
				node.setInventoryDetail(inventoryMapper.toInventoryDetail(optInventory.get()));

			if (node.getInventoryDetail() != null) {
				status = node.getInventoryDetail().getStatus();
				node.setImage(getOutletIconFromStatus(node.getNodeType(), status));
				balance = node.getInventoryDetail().getCurrentBalance();
				consumedQty = node.getInventoryDetail().getConsumedQty();
				temperature = node.getInventoryDetail().getTemperature();
			}

			nodes.add(node);
			edges.add(TopologyMapper.toEdgeRepresentation(outlet, 70006));
			String color = getOutletColorFromStatus(status);
			// System.out.println("color of node: "+ color);
			geoData += MapDataBuilder.nodeData(color, node.getLabel(), node.getLabel(),
					getOutletIconFromStatus(node.getNodeType(), status), "image", temperature, balance, consumedQty, "",
					node.getLat(), node.getLng(), node.getNodeType(), node.getGroup(), false);
			geoDataExist++;
		}
		nodes.add(buildMinisteryode());
		String geo = "";

		if (geoDataExist > 0) // data exists
			geo = geoData.substring(0, geoData.length() - 1);
		else
			geo = geoData; // data does not exist
		geo += MapDataBuilder.footerGeo();
		KpiTable kpiTable = KpiTable.builder().build();
		if (overallStatsOutlet != null && !overallStatsOutlet.isEmpty()) {
			kpiTable.setNumberOfVaccine(overallStatsOutlet.get(0).getTotalVaccinesReceivedAtOutlets());
			kpiTable.setConsumedVaccine(overallStatsOutlet.get(0).getTotalVaccinesConsumed());
			kpiTable.setNumberOfOutlets(outlets.size());
		}

		return TopologyRepresentation.builder().kpiTable(kpiTable).nodes(nodes).edges(edges).geoData(geo).build();
	}

	// ######################################" Map by Status or by Temperature
	// ################################"

	@Override
	public TopologyRepresentation buildTopologyDataWithParam(String statusOrTemperature)
			throws TopologyDataNotFoundException {
		/** get all network elements */
		DecimalFormat formatter = (DecimalFormat)NumberFormat.getNumberInstance(Locale.US);
		formatter.applyPattern("##.#");
		List<Outlet> outlets = outletService.findAll();
		List<DeviceOverviewStats> deviceOverviewStats = deviceOverviewStatsService.findAll();
		List<NodeRepresentation> nodes = new ArrayList<NodeRepresentation>();
		List<EdgeRepresentation> edges = new ArrayList<>();
		List<OutletOverviewProjection> overallStatsOutlet = overallStatsRepository.getKpiOutletCustomQuery("all");
		int geoDataExist = 0;
		geoData = MapDataBuilder.headerGeo();
		for (Outlet outlet : outlets) {

			/** get a list of devices */
			List<DeviceOverviewStats> deviceOverviewStatsFiltered = deviceOverviewStats.stream()
					.filter(device -> device.getOutlet().getOutletName().equals(outlet.getOutletName()))
					.collect(Collectors.toList());

			/** calculate the health */
			String outletHealth = getOutletHealth(deviceOverviewStatsFiltered);

			NodeRepresentation node = TopologyMapper.toNodeRepresentation(outlet, outletHealth);
			String status = "";
			String temperature = "";

			Example<Category> exampleCategory = Example.of(Category.builder().categoryCode("COVID VACCINE").build());
			Optional<Category> optionalCategory = categoryRepository.findOne(exampleCategory);
			if (optionalCategory.isPresent()) {
				Example<Product> exampleProduct = Example
						.of(Product.builder().category(optionalCategory.get()).build());
				Optional<Product> optionalProduct = productRepository.findOne(exampleProduct);
				if (optionalProduct.isPresent()) {
					Inventory inventory = Inventory.builder().isLastInstance(Boolean.TRUE).outlet(outlet)
							.product(optionalProduct.get()).build();
					Example<Inventory> exampleInventory = Example.of(inventory);

					Optional<Inventory> optInventory = inventoryRepository.findOne(exampleInventory);
					// Optional<Inventory> optInventory =
					// inventoryRepository.findByOutletOutletId(outlet.getOutletId()).stream().findFirst();
					if (optInventory.isPresent())
						node.setInventoryDetail(inventoryMapper.toInventoryDetail(optInventory.get()));

					// ################## Map By Status ##############################"
					if (statusOrTemperature.equals("status")) {
						double balance = 0;
						double consumedQty = 0;
						if (node.getInventoryDetail() != null) {
							balance = node.getInventoryDetail().getCurrentBalance();
							consumedQty = node.getInventoryDetail().getConsumedQty();
							status = node.getInventoryDetail().getStatus();
							node.setImage(getOutletIconFromStatus(node.getNodeType(), status));
						}
						String color = getOutletColorFromStatus(status);
						geoData += MapDataBuilder.nodeData(color, node.getLabel(), node.getLabel(),
								getOutletIconFromStatus(node.getNodeType(), status), "image", "", balance, consumedQty,
								"", node.getLat(), node.getLng(), node.getNodeType(), node.getGroup(), false);
						geoDataExist++;
					}

					// ################## Map By Temperature ##############################"
					if (statusOrTemperature.equals("temperature")) {
						//*
						 String color = "blue";
					//	 List<DeviceOverviewStats> deviceOverviewStatsByOutlet = deviceOverviewStatsService.findByOutletOutletId(outlet.getOutletId());
						 List<DeviceOverviewStats> deviceOverviewStatsByOutlet = deviceOverviewStatsService.findByOutlet(outlet);

					//	 List<Double> temperatures = new ArrayList<Double>() ;
					//	 deviceOverviewStatsByOutlet.forEach(device -> temperatures.add(device.getTemperature())); 
						 List<Double> temperatures = deviceOverviewStatsByOutlet.stream().map(DeviceOverviewStats::getTemperature).collect(Collectors.toList());
						 color = getOutletColorFromTemperatures(temperatures); 

					     String temperaturesToSend = deviceOverviewStatsByOutlet.stream().map(DeviceOverviewStats::getTemperature)
					    		 											.map(temp -> formatter.format(temp)).collect(Collectors.joining(","));
						 
					     if(temperaturesToSend.isEmpty()) temperaturesToSend = "NA";
					     // */
						/*
						String color = "blue";
						if (node.getInventoryDetail() != null) {
							temperature = node.getInventoryDetail().getTemperature();
							node.setImage(
									getOutletIconFromTemperature(node.getNodeType(), Double.parseDouble(temperature)));
							color = getOutletColorFromTemperature(Double.parseDouble(temperature));

						}
						//*/
						geoData += MapDataBuilder.nodeData(color, node.getLabel(), node.getLabel(),	getOutletIconFromStatus(node.getNodeType(), status), "image", temperaturesToSend, null, null,
								"", node.getLat(), node.getLng(), node.getNodeType(), node.getGroup(), false);
						geoDataExist++;
					}

					nodes.add(node);
					edges.add(TopologyMapper.toEdgeRepresentation(outlet, 70006));
				}
			}
		}
		nodes.add(buildMinisteryode());
		String geo = "";

		if (geoDataExist > 0) // data exists
			geo = geoData.substring(0, geoData.length() - 1);
		else
			geo = geoData; // data does not exist

		geo += MapDataBuilder.footerGeo();
		KpiTable kpiTable = KpiTable.builder().build();
		if (overallStatsOutlet != null && !overallStatsOutlet.isEmpty()) {
			kpiTable.setNumberOfVaccine(overallStatsOutlet.get(0).getTotalVaccinesReceivedAtOutlets());
			kpiTable.setConsumedVaccine(overallStatsOutlet.get(0).getTotalVaccinesConsumed());
			kpiTable.setNumberOfOutlets(outlets.size());
		}

		return TopologyRepresentation.builder().kpiTable(kpiTable).nodes(nodes).edges(edges).geoData(geo).build();
	}

	// ################################" STATUS
	// ######################################""""
	private String getOutletIconFromStatus(String nodeType, String status) {
		if (status.equals("in")) {
			if (nodeType.equalsIgnoreCase("phc"))
				return TopologyConstant.PHC_GREEN_ICON;
			else if (nodeType.equalsIgnoreCase("Hospital"))
				return TopologyConstant.HO_GREEN_ICON;
			else if (nodeType.equalsIgnoreCase("DrivethroughCenter"))
				return TopologyConstant.DT_GREEN_ICON;
			else if (nodeType.equalsIgnoreCase("WalkthroughCenter"))
				return TopologyConstant.WT_GREEN_ICON;
			return TopologyConstant.HO_GREEN_ICON;
		}

		else if (status.equals("noos")) {
			if (nodeType.equalsIgnoreCase("phc"))
				return TopologyConstant.PHC_ORANGE_ICON;
			else if (nodeType.equalsIgnoreCase("Hospital"))
				return TopologyConstant.HO_ORANGE_ICON;
			else if (nodeType.equalsIgnoreCase("DrivethroughCenter"))
				return TopologyConstant.DT_ORANGE_ICON;
			else if (nodeType.equalsIgnoreCase("WalkthroughCenter"))
				return TopologyConstant.WT_ORANGE_ICON;
			return TopologyConstant.HO_ORANGE_ICON;
		}

		else if (status.equals("oos")) {
			if (nodeType.equalsIgnoreCase("phc"))
				return TopologyConstant.PHC_RED_ICON;
			else if (nodeType.equalsIgnoreCase("Hospital"))
				return TopologyConstant.HO_RED_ICON;
			else if (nodeType.equalsIgnoreCase("DrivethroughCenter"))
				return TopologyConstant.DT_RED_ICON;
			else if (nodeType.equalsIgnoreCase("WalkthroughCenter"))
				return TopologyConstant.WT_RED_ICON;
			return TopologyConstant.HO_RED_ICON;
		} else {
			if (nodeType.equalsIgnoreCase("phc"))
				return TopologyConstant.PHC_ICON;
			else if (nodeType.equalsIgnoreCase("Hospital"))
				return TopologyConstant.HO_ICON;
			else if (nodeType.equalsIgnoreCase("DrivethroughCenter"))
				return TopologyConstant.DT_ICON;
			else if (nodeType.equalsIgnoreCase("WalkthroughCenter"))
				return TopologyConstant.WT_ICON;
			return TopologyConstant.HO_ICON;
		}

	}

	private String getOutletColorFromStatus(String status) {
		if (status.equals("in"))
			return TopologyConstant.OUTLET_NORMAL_HEALTH;
		else if (status.equals("noos"))
			return TopologyConstant.OUTLET_DEGRADED_HEALTH;
		else if (status.equals("oos"))
			return TopologyConstant.OUTLET_CRITICAL_HEALTH;
		return TopologyConstant.OUTLET_NA_HEALTH;
	}

	// ########################### TEMPERATURE
	// #######################################"""

	private String getOutletIconFromTemperature(String nodeType, Double temperature) {
		if (temperature == null) {
			if (nodeType.equalsIgnoreCase("phc"))
				return TopologyConstant.PHC_ICON;
			else if (nodeType.equalsIgnoreCase("Hospital"))
				return TopologyConstant.HO_ICON;
			else if (nodeType.equalsIgnoreCase("DrivethroughCenter"))
				return TopologyConstant.DT_ICON;
			else if (nodeType.equalsIgnoreCase("WalkthroughCenter"))
				return TopologyConstant.WT_ICON;
			return TopologyConstant.HO_ICON;
		}
		if (temperature < outletKpiTemperatureMin || temperature > outletKpiTemperatureMax) {
			if (nodeType.equalsIgnoreCase("phc"))
				return TopologyConstant.PHC_RED_ICON;
			else if (nodeType.equalsIgnoreCase("Hospital"))
				return TopologyConstant.HO_RED_ICON;
			else if (nodeType.equalsIgnoreCase("dt"))
				return TopologyConstant.DT_RED_ICON;
			else if (nodeType.equalsIgnoreCase("WalkthroughCenter"))
				return TopologyConstant.WT_RED_ICON;
			return TopologyConstant.HO_RED_ICON;
		} else {
			if (nodeType.equalsIgnoreCase("phc"))
				return TopologyConstant.PHC_GREEN_ICON;
			else if (nodeType.equalsIgnoreCase("Hospital"))
				return TopologyConstant.HO_GREEN_ICON;
			else if (nodeType.equalsIgnoreCase("DrivethroughCenter"))
				return TopologyConstant.DT_GREEN_ICON;
			else if (nodeType.equalsIgnoreCase("WalkthroughCenter"))
				return TopologyConstant.WT_GREEN_ICON;
			return TopologyConstant.HO_GREEN_ICON;
		}
	}

	private String getOutletColorFromTemperature(Double temperature) {
		if (temperature < outletKpiTemperatureMin || temperature > outletKpiTemperatureMax)
			return TopologyConstant.OUTLET_CRITICAL_HEALTH;
		return TopologyConstant.OUTLET_NORMAL_HEALTH;
	}

	private String getOutletColorFromTemperatures(List<Double> temperatures) {
		if(temperatures.isEmpty()) return TopologyConstant.OUTLET_NA_HEALTH;
		for (Double temperature : temperatures) {
			if (temperature < outletKpiTemperatureMin || temperature > outletKpiTemperatureMax)
				return TopologyConstant.OUTLET_CRITICAL_HEALTH;
		}
		return TopologyConstant.OUTLET_NORMAL_HEALTH;
	}

	// #########################################################""

	private String buildMapIconByNodeType(String nodeType) {
		if (nodeType.equalsIgnoreCase("phc"))
			return TopologyConstant.PHC_ICON;
		else if (nodeType.equalsIgnoreCase("Hospital"))
			return TopologyConstant.HO_ICON;
		else if (nodeType.equalsIgnoreCase("DrivethroughCenter"))
			return TopologyConstant.DT_ICON;
		else if (nodeType.equalsIgnoreCase("WalkthroughCenter"))
			return TopologyConstant.WT_ICON;
		return TopologyConstant.HO_ICON;
	}

	@Override
	public List<NodeIdListRepresentation> getDistinctNodeId() {
		return TopologyMapper.toUiMultiList(outletService.findAll());
	}

	private String getOutletHealth(List<DeviceOverviewStats> deviceOverviewStats) {
		if (!Optional.ofNullable(deviceOverviewStats).isPresent())
			return TopologyConstant.OUTLET_NA_HEALTH;
		String health = deviceOverviewStats.stream().map(device -> hasBadTemperature(device))
				.map(badTemp -> TopologyConstant.OUTLET_CRITICAL_HEALTH).findFirst()
				.orElse(TopologyConstant.OUTLET_NORMAL_HEALTH);
		if (!health.equals(TopologyConstant.OUTLET_CRITICAL_HEALTH)) {
			health = deviceOverviewStats.stream().map(device -> hasBadHumidity(device))
					.map(badTemp -> TopologyConstant.OUTLET_DEGRADED_HEALTH).findFirst()
					.orElse(TopologyConstant.OUTLET_NORMAL_HEALTH);
		}
		if (!health.equals(TopologyConstant.OUTLET_DEGRADED_HEALTH)) {
			health = deviceOverviewStats.stream().map(device -> hasBadPressure(device))
					.map(badTemp -> TopologyConstant.OUTLET_DEGRADED_HEALTH).findFirst()
					.orElse(TopologyConstant.OUTLET_NORMAL_HEALTH);
		}
		return health;
	}

	/**
	 * Temper check
	 * 
	 * @param device
	 * @return
	 */
	private boolean hasBadTemperature(DeviceOverviewStats device) {
		if (device.getTemperature() < outletKpiTemperatureMin || device.getTemperature() > outletKpiTemperatureMax)
			return true;
		return false;
	}

	/**
	 * Humidity check
	 * 
	 * @param device
	 * @return
	 */
	private boolean hasBadHumidity(DeviceOverviewStats device) {
		if (device.getHumidity() < outletKpiHumidityMin || device.getHumidity() > outletKpiHumidityMax)
			return true;
		return false;
	}

	/**
	 * Pressure check
	 * 
	 * @param device
	 * @return
	 */
	private boolean hasBadPressure(DeviceOverviewStats device) {
		if (device.getPressure() < outletKpiPressureMin || device.getPressure() > outletKpiPressureMax)
			return true;
		return false;
	}

	private NodeRepresentation buildMinisteryode() {
		return NodeRepresentation.builder().id(70006).label("MOH").shape("image").image(TopologyConstant.MIN_ICON)
				.nodeType("MIN").region("all").parentRegion("all").group("MIN").build();
	}
}
