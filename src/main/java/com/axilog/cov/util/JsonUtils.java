package com.axilog.cov.util;

import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.ObjectMapper;

import com.axilog.cov.dto.representation.PoPdfDetail;

public class JsonUtils {

	/**
	 * Utils method to convert object to json
	 * @param object
	 * @return
	 */
	public static String toJsonString(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		try {
		  return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
		   e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @param json
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public static PoPdfDetail toJsonObject(String json) throws JsonMappingException, JsonProcessingException {
		return new ObjectMapper().readValue(json, PoPdfDetail.class);
	}
}
