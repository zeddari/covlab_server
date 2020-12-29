package com.axilog.cov.service.impl.topology;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.axilog.cov.constant.TopologyConstant;
import com.axilog.cov.domain.Category;
import com.axilog.cov.domain.DeviceOverviewStats;
import com.axilog.cov.domain.Inventory;
import com.axilog.cov.domain.Outlet;
import com.axilog.cov.domain.Product;
import com.axilog.cov.dto.mapper.InventoryMapper;
import com.axilog.cov.dto.mapper.TopologyMapper;
import com.axilog.cov.dto.topology.MapDataBuilder;
import com.axilog.cov.dto.topology.representation.EdgeRepresentation;
import com.axilog.cov.dto.topology.representation.NodeIdListRepresentation;
import com.axilog.cov.dto.topology.representation.NodeRepresentation;
import com.axilog.cov.dto.topology.representation.TopologyRepresentation;
import com.axilog.cov.exception.TopologyDataNotFoundException;
import com.axilog.cov.repository.InventoryRepository;
import com.axilog.cov.service.DeviceOverviewStatsService;
import com.axilog.cov.service.OutletService;
import com.axilog.cov.service.topology.TopologyService;

@Service
public class TopologyServiceImpl implements TopologyService {
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
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
	
	@Override
	public TopologyRepresentation buildTopologyData() throws TopologyDataNotFoundException {
		/** get all network elements */
		List<Outlet> outlets = outletService.findAll();
		List<DeviceOverviewStats> deviceOverviewStats = deviceOverviewStatsService.findAll();
		List<NodeRepresentation> nodes = new ArrayList<NodeRepresentation>();
		List<EdgeRepresentation> edges = new ArrayList<>();
		geoData = MapDataBuilder.headerGeo();
		outlets.forEach(outlet -> {
			
			/** get a list of devices */
			List<DeviceOverviewStats> deviceOverviewStatsFiltered = deviceOverviewStats.stream().filter(device -> device.getOutlet().getOutletName().equals(outlets)).collect(Collectors.toList());
			
			/** calculate the health */
			String outletHealth = getOutletHealth(deviceOverviewStatsFiltered);
			NodeRepresentation node = TopologyMapper.toNodeRepresentation(outlet, outletHealth);
			
			/** get details of outlet inventory */
			Inventory inventory = Inventory.builder().isLastInstance(true).outlet(outlet).product(Product.builder().category(Category.builder().id(9L).build()).build()).build();
			Example<Inventory> exampleInventory= Example.of(inventory);
			Optional<Inventory> optInventory = inventoryRepository.findOne(exampleInventory);
			if (optInventory.isPresent()) {
				node.setInventoryDetail(inventoryMapper.toInventoryDetail(optInventory.get()));
			}
			nodes.add(node);
			edges.add(TopologyMapper.toEdgeRepresentation(outlet, 70006));
			double balance = 0;
			double consumedQty = 0;
			if (node.getInventoryDetail() != null) {
				balance = node.getInventoryDetail().getCurrentBalance();
				consumedQty = node.getInventoryDetail().getConsumedQty();
			}
			geoData += MapDataBuilder.nodeData(node.getFont().getColor(), node.getLabel(), node.getLabel(), buildMapIconByNodeType(node.getNodeType()), "image", balance, consumedQty, "", node.getLat(), node.getLng(), node.getNodeType(), node.getGroup(), false);

		});
		nodes.add(buildMinisteryode());
		String geo = geoData.substring(0, geoData.length() -1);
		geo +=MapDataBuilder.footerGeo();
		return TopologyRepresentation.builder().nodes(nodes).edges(edges).geoData(geo).build();
	}


	private String buildMapIconByNodeType(String nodeType) {
		if (nodeType.equalsIgnoreCase("phc")) return TopologyConstant.PHC_ICON;
		else if (nodeType.equalsIgnoreCase("ho")) return TopologyConstant.HO_ICON;
		else if (nodeType.equalsIgnoreCase("dt")) return TopologyConstant.DT_ICON;
		else if (nodeType.equalsIgnoreCase("wt")) return TopologyConstant.WT_ICON;
		return TopologyConstant.HO_ICON;
	}

	@Override
	public List<NodeIdListRepresentation> getDistinctNodeId() {
		return TopologyMapper.toUiMultiList(outletService.findAll());
	}
	
	private String getOutletHealth(List<DeviceOverviewStats> deviceOverviewStats) {
		if (!Optional.ofNullable(deviceOverviewStats).isPresent()) return TopologyConstant.OUTLET_NA_HEALTH;
		String health = deviceOverviewStats.stream().map(device -> hasBadTemperature(device)).map(badTemp -> TopologyConstant.OUTLET_CRITICAL_HEALTH).findFirst().orElse(TopologyConstant.OUTLET_NORMAL_HEALTH);
		if (!health.equals(TopologyConstant.OUTLET_CRITICAL_HEALTH)) {
			health = deviceOverviewStats.stream().map(device -> hasBadHumidity(device)).map(badTemp -> TopologyConstant.OUTLET_DEGRADED_HEALTH).findFirst().orElse(TopologyConstant.OUTLET_NORMAL_HEALTH);
		}
		if (!health.equals(TopologyConstant.OUTLET_DEGRADED_HEALTH)) {
			health = deviceOverviewStats.stream().map(device -> hasBadPressure(device)).map(badTemp -> TopologyConstant.OUTLET_DEGRADED_HEALTH).findFirst().orElse(TopologyConstant.OUTLET_NORMAL_HEALTH);
		}
		return health;
	}

	/**
	 * Temper check
	 * @param device
	 * @return
	 */
	private boolean hasBadTemperature(DeviceOverviewStats device) {
		if (device.getTemperature() < outletKpiTemperatureMin || device.getTemperature() > outletKpiTemperatureMax) return true;
		return false;
	}
	
	/**
	 * Humidity check
	 * @param device
	 * @return
	 */
	private boolean hasBadHumidity(DeviceOverviewStats device) {
		if (device.getHumidity() < outletKpiHumidityMin || device.getHumidity() > outletKpiHumidityMax) return true;
		return false;
	}
	
	/**
	 * Pressure check
	 * @param device
	 * @return
	 */
	private boolean hasBadPressure(DeviceOverviewStats device) {
		if (device.getPressure() < outletKpiPressureMin || device.getPressure() > outletKpiPressureMax) return true;
		return false;
	}
	
	private NodeRepresentation buildMinisteryode() {
		return NodeRepresentation.builder()
				.id(70006)
				.label("MOH")
				.shape("image")
				.image(TopologyConstant.MIN_ICON)
				.nodeType("MIN")
				.region("all")
				.parentRegion("all")
				.group("MIN")
				.build();
	}
}
