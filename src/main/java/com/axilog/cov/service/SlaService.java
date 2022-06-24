package com.axilog.cov.service;

import com.axilog.cov.exception.RequestNotFoundException;

public interface SlaService {

	void startSla(String applicationId, String context);

	void endSla(String applicationId, String context) throws RequestNotFoundException;
	
	void endSla(String applicationId, String context, Boolean outOfSla);

}
