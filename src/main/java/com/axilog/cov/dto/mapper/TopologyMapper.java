package com.axilog.cov.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import com.axilog.cov.constant.TopologyConstant;
import com.axilog.cov.domain.Inventory;
import com.axilog.cov.dto.topology.representation.EdgeRepresentation;
import com.axilog.cov.dto.topology.representation.Font;
import com.axilog.cov.dto.topology.representation.KpiCategory;
import com.axilog.cov.dto.topology.representation.NodeIdListRepresentation;
import com.axilog.cov.dto.topology.representation.NodeRepresentation;


@Component
public class TopologyMapper {

	/**
	 * @param nodeIds
	 * @return
	 */
	public List<NodeIdListRepresentation> toUiMultiList(List<String> nodeIds) {
		AtomicInteger index = new AtomicInteger();
		List<NodeIdListRepresentation> nodeIdsRepre = new ArrayList<>();
		if (nodeIds == null) return new ArrayList<NodeIdListRepresentation>();
		nodeIds.forEach( nodeId -> nodeIdsRepre.add(NodeIdListRepresentation.builder().item_id(index.getAndIncrement()).item_text(nodeId).build()));
		return nodeIdsRepre;
	}
	
	/**
	 * @param ne
	 * @return
	 */
	public static NodeRepresentation toNodeRepresentation(Inventory ne) {
		return NodeRepresentation.builder()
		.id(ne.getId().intValue())
		.font(Font.builder().align("bottom").color(TopologyConstant.NODE_TITLE_COLOR_ORANGE).size(TopologyConstant.NODE_TITLE_SIZE).build())
		.image(ne.getOutlet().getOutletType())
		.label(ne.getOutlet().getOutletName())
		.shape("image")
		.build();
		
		
	}
	
	/**
	 * @param kpiCategories
	 * @param category
	 * @return
	 */
	private static Optional<KpiCategory> findCategoryByName(List<KpiCategory>kpiCategories, String category) {
		if (kpiCategories == null) return Optional.empty();
		return kpiCategories.stream().filter(cat -> cat.getCategoryCode().equals(category)).findFirst();
	}
	
	private static void removeCategoryByName(List<KpiCategory> kpiCategories, String category) {
		if (kpiCategories == null) return;
		Optional<KpiCategory> optCat = kpiCategories.stream().filter(cat -> cat.getCategoryCode().equals(category)).findFirst();
		if (optCat.isPresent()) kpiCategories.remove(optCat.get());
	}
	
	/**
	 * @param link
	 * @return
	 */
	public static EdgeRepresentation toEdgeRepresentation(Inventory ne, Integer ministeryId) {
		return EdgeRepresentation.builder()
				.id(ministeryId + "-" + ne.getId())
				.to(ne.getId().intValue())
				.from(ministeryId)
				.build();
	}
}
