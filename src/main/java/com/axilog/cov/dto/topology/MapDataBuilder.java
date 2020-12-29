package com.axilog.cov.dto.topology;

import com.axilog.cov.dto.representation.InventoryDetail;

public class MapDataBuilder {
	
	public static String headerGeo() {
		return "{ \"type\": \"FeatureCollection\", \"crs\": { \"type\": \"name\", \"properties\": { \"name\": \"urn:ogc:def:crs:OGC:1.3:CRS84\" } },\"features\": [";
	}

	
	public static String nodeData(String color, String nodeId, String nodeName, String image, String shape, Double balance, Double consumed, String textModal,  String lat, String lng, String nodeType,  String group, Boolean fixed) {
	return "{ \"type\": \"Feature\", \"properties\": { \"color\": \"" + color + "\", \"nodeId\": \"" + nodeId + "\", \"nodeName\": \"" + nodeName + "\", \"image\": \"" + image + "\", \"shape\": \"" + shape + "\", \"balance\": \"" + balance + "\", \"consumed\": \"" + consumed +"\", \"textModal\": \"" + textModal + "\", \"lat\": \"" + lat + "\", \"lng\": \"" + lng + "\", \"nodeType\": \"" + nodeType + "\" , \"group\": \"" + group + "\", \"fixed\": \"" + fixed + "\"  }, \"geometry\": { \"type\": \"Point\", \"coordinates\": [ " + lng + "," +  lat + "] } },";
	
	}
	
	public static String footerGeo() {
		return "]}";
		}
}
