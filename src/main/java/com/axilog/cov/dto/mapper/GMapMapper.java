package com.axilog.cov.dto.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.axilog.cov.constant.TopologyConstant;
import com.axilog.cov.dto.topology.command.CoordinateDto;
import com.axilog.cov.dto.topology.representation.EdgeRepresentation;
import com.axilog.cov.dto.topology.representation.MapRepresentation;
import com.axilog.cov.dto.topology.representation.MarkerRepresentation;
import com.axilog.cov.dto.topology.representation.NodeRepresentation;
import com.axilog.cov.dto.topology.representation.PolylineRepresentation;
import com.axilog.cov.dto.topology.representation.SummaryRepresentationForCircle;
import com.axilog.cov.dto.topology.representation.TopologyRepresentation;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class GMapMapper {

	int x=0;
	Set<CoordinateDto> coordinateDtos = new HashSet<>();
	int nodeNumber; 
	String sumCircleColor;
	boolean redCircle= false, orangeCircle= false, greenCircle= false, blueCircle= false;
	/**
	 * @param topologyRepresentation
	 * @return
	 */
	public MapRepresentation toMap(TopologyRepresentation topologyRepresentation) {
		coordinateDtos = new HashSet<>();
		List<MarkerRepresentation> markers = new ArrayList<>();
		List<PolylineRepresentation> polylines = new ArrayList<>();
		topologyRepresentation.getEdges().forEach(edge -> polylines.add(toPolyline(edge)));
		topologyRepresentation.getNodes().stream().filter(node -> !node.getNodeType().equals("CUSTOMER"))
							.filter(node -> !node.getNodeType().contains("LEGEND"))
							.filter(node -> node.getLat() != null && node.getLng() != null)
							.forEach(node -> markers.add(toMarker(node, topologyRepresentation)));
		String geoData = topologyRepresentation.getGeoData();
		String geoDataS = topologyRepresentation.getGeoDataService();
		String geoDataO = topologyRepresentation.getGeoDataOther();
		return MapRepresentation.builder().markers(markers).polylines(polylines)
				.geoData(geoData).geoDataService(geoDataS).geoDataOther(geoDataO)
				.executiveTable(topologyRepresentation.getExecutiveTable())
			.sumRepForCircle(SummaryRepresentationForCircle.builder().nodeNumber(markers.size()).color(sumCircleColor=nodeColorForSummaryCircle(markers)).build())
			.build();
	}
	
	
	/**
	 * @param node
	 * @return
	 */
	public MarkerRepresentation toMarker(NodeRepresentation node, TopologyRepresentation topologyRepresentation) {
		List<PolylineRepresentation> polylines = new ArrayList<>();
		topologyRepresentation.getEdges().stream().filter(edge -> edge.getFrom().equals(Integer.toString(node.getId()))).collect(Collectors.toList()).forEach(edge -> polylines.add(toPolyline(edge)));
		x +=polylines.size();
		topologyRepresentation.getEdges().stream().filter(edge -> edge.getTo().equals(Integer.toString(node.getId()))).collect(Collectors.toList()).forEach(edge -> polylines.add(toPolyline(edge)));
		x +=polylines.size();

		
		CoordinateDto coordinateDto = CoordinateDto.builder().lattitude(node.getLat()).longitude(node.getLng()).build();
		boolean circleToDisplay = true;
		if (!coordinateDtos.add(coordinateDto) && node.getImage().contains("GREEN")) {
			circleToDisplay = false;
		}
		polylines.clear();
		return MarkerRepresentation.builder().icon(node.getImage()).label(node.getLabel()).label2(node.getLabel2()).lat(node.getLat()).lng(node.getLng()).draggable(false).type(node.getNodeType())
				.circleToDisplay(circleToDisplay)
				.iconHeight(getIconHeight(node.getNodeType()))
				.iconWidth(40)
				.color(markerColor(node.getImage())).animation(markerAnimation(node.getImage()))
				.polylines(null)
				.htmlModal(node.getTextModal())
				.info1(node.getInfo1()).info21(node.getInfo21()).info22(node.getInfo22()).info3(node.getInfo3()).info4(node.getInfo4())
				.info5(node.getInfo5()).info6(node.getInfo6()).info7(node.getInfo7()).info8(node.getInfo8())
				.visible(false)
				.build();
	}
	/**
	 * @param edge
	 * @return
	 */
	public PolylineRepresentation toPolyline(EdgeRepresentation edge) {
		return PolylineRepresentation.builder().latDest(edge.getLatDest()).latSrc(edge.getLatSrc())
				.lngDest(edge.getLngDest()).lngSrc(edge.getLngSrc())
				.textLinkModal(edge.getTextLinkModal())
				.color(edge.getColor()).build();
	}
	
	/**
	 * @param type
	 * @return
	 */
	private Integer getIconHeight(String type) {
		if (type == null) return 40;
		if (type.equals("CSG") || type.equals("UPE") || type.equals("TXM") || type.equals("CUSTOMER")
		 || type.equals("FON") || type.equals("SNFN") || type.equals("EROUTER") || type.equals("SATELLITE") 
			|| type.equals("OLT") || type.equals("WIFI") || type.equals("SERVICE") || type.equals("COR")) 
			return 40;
		else return 80;
	}
	
	private String markerColor(String nodeColor)
	{
		String color= TopologyConstant.NODE_TITLE_COLOR_BLUE; 
		if(nodeColor.toUpperCase().contains("GREEN")) color=TopologyConstant.NODE_TITLE_COLOR_GREEN; 
		else if (nodeColor.toUpperCase().contains("ORANGE"))	color=TopologyConstant.NODE_TITLE_COLOR_ORANGE;
		else	color=TopologyConstant.NODE_TITLE_COLOR_RED;
		return color;
	}
	
	private String markerAnimation(String nodeColor)
	{
		String animation=""; 
		if(nodeColor.contains("RED")) animation="BOUNCE"; 
		else if (nodeColor.contains("ORANGE"))	animation="";
		return animation;
	}
	
	public String nodeColorForSummaryCircle(List<MarkerRepresentation> markers) {
		for (MarkerRepresentation mark : markers) {
			if(mark.getColor().equals(TopologyConstant.NODE_TITLE_COLOR_ORANGE)) 
				orangeCircle= true	;	
			else if(mark.getColor().equals(TopologyConstant.NODE_TITLE_COLOR_RED)) 
				redCircle= true	;	
			else if(mark.getColor().equals(TopologyConstant.NODE_TITLE_COLOR_GREEN)) 
				greenCircle= true	;
			else blueCircle= true	;	
			}
		if(redCircle==true)  return "red";
		else if (orangeCircle==true)		return "orange";
		else if (greenCircle==true)	return "green";
		else  return "blue";
	}
	
	public void geoJson() {
		
	}
}

