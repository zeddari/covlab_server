package com.axilog.cov.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.axilog.cov.constant.TopologyConstant;
import com.axilog.cov.domain.Outlet;
import com.axilog.cov.dto.topology.representation.EdgeRepresentation;
import com.axilog.cov.dto.topology.representation.Font;
import com.axilog.cov.dto.topology.representation.NodeIdListRepresentation;
import com.axilog.cov.dto.topology.representation.NodeRepresentation;


@Component
public class TopologyMapper {

	/**
	 * @param nodeIds
	 * @return
	 */
	public static List<NodeIdListRepresentation> toUiMultiList(List<Outlet> outlets) {
		List<NodeIdListRepresentation> nodeIdsRepre = new ArrayList<>();
		if (outlets == null) return new ArrayList<NodeIdListRepresentation>();
		outlets.forEach( outlet -> nodeIdsRepre.add(NodeIdListRepresentation.builder().item_id(outlet.getId().intValue()).item_text(outlet.getOutletAdress()).build()));
		return nodeIdsRepre;
	}
	
	/**
	 * @param ne
	 * @return
	 */
	public static NodeRepresentation toNodeRepresentation(Outlet outlet, String color) {
		return NodeRepresentation.builder()
		.id(outlet.getId().intValue())
		.font(Font.builder().align("bottom").color(color).size(TopologyConstant.NODE_TITLE_SIZE).build())
		.image(chooseIconMedByType(outlet.getOutletType(), color))
		.label(outlet.getOutletName())
		.label2(outlet.getOutletAdress())
		.nodeType(outlet.getOutletType())
		.group(outlet.getOutletType())
		.region(outlet.getOutletRegion())
		.parentRegion(outlet.getOutletParentRegion())
		.lat(Double.toString(outlet.getOutletLat()))
		.lng(Double.toString(outlet.getOutletLng()))
		.shape("image")
		.build();
		
		
	}
	
	/**
	 * @param link
	 * @return
	 */
	public static EdgeRepresentation toEdgeRepresentation(Outlet outlet, Integer ministeryId) {
		return EdgeRepresentation.builder()
				.id(ministeryId + "-" + outlet.getId())
				.type("Min-Outlet")
				.to(outlet.getId().intValue())
				.from(ministeryId)
				.parentRegion(outlet.getOutletParentRegion())
				.build();
	}
	
	private static String chooseIconMedByType(String type, String severity) {
		String icon = TopologyConstant.NODE_BLUE_ICON_NA;
		/*
		String icon = "";
		if (type == null) return TopologyConstant.NODE_BLUE_ICON_NA;
		type = type.replace("", "");
		if (type.equalsIgnoreCase("phc")) {
			if (severity.equals("red")) {
				icon = TopologyConstant.PHC_RED_ICON;
			}
			else if (severity.equals("orange")) {
				icon = TopologyConstant.PHC_ORANGE_ICON;
			}
			else if (severity.equals("green")) {
				icon = TopologyConstant.PHC_GREEN_ICON;
			}
			else {
				icon = TopologyConstant.PHC_BLUE_ICON;
			}
		 }
		else if (type.equalsIgnoreCase("hospital")) {
			if (severity.equals("red")) {
				icon = TopologyConstant.HO_RED_ICON;
			}
			else if (severity.equals("orange")) {
				icon = TopologyConstant.HO_ORANGE_ICON;
			}
			else if (severity.equals("green")) {
				icon = TopologyConstant.HO_GREEN_ICON;
			}
			else {
				icon = TopologyConstant.HO_BLUE_ICON;
			}
			
		 }
		else if (type.equalsIgnoreCase("walkthroughcenter")) {
			if (severity.equals("red")) {
				icon = TopologyConstant.WT_RED_ICON;
			}
			else if (severity.equals("orange")) {
				icon = TopologyConstant.WT_ORANGE_ICON;
			}
			else if (severity.equals("green")) {
				icon = TopologyConstant.WT_GREEN_ICON;
			}
			else {
				icon = TopologyConstant.WT_BLUE_ICON;
			}
			
		 }
		else if (type.equalsIgnoreCase("drivethroughcenter")) {
			if (severity.equals("red")) {
				icon = TopologyConstant.DT_RED_ICON;
			}
			else if (severity.equals("orange")) {
				icon = TopologyConstant.DT_ORANGE_ICON;
			}
			else if (severity.equals("green")) {
				icon = TopologyConstant.DT_GREEN_ICON;
			}
			else {
				icon = TopologyConstant.DT_BLUE_ICON;
			}
			
		 }
		*/
		return icon;
	}
	
}
