package com.axilog.cov.util;

import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.ObjectMapper;

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
}
