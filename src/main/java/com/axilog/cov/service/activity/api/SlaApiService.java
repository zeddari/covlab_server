package com.axilog.cov.service.activity.api;

import com.axilog.cov.exception.RequestNotFoundException;

public interface SlaApiService {

	void startSla(String applicationId, String context);

	void endSla(String applicationId, String context) throws RequestNotFoundException;
	
	void endSla(String applicationId, String context, Boolean outOfSla);

}
