package com.axilog.cov.service.activity.bean;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TaskNotsignedBean {
	
	public void log(String applicationId) {
		
		log.debug("the quotation was not signed for the applicationId : " + applicationId);
	}
}
