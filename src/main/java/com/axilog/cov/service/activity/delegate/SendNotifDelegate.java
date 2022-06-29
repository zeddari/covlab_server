package com.axilog.cov.service.activity.delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axilog.cov.service.OtpMailService;
import com.axilog.cov.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SendNotifDelegate implements JavaDelegate {

	@Autowired
	OtpMailService otpMailService;

	@Override
	public void execute(DelegateExecution execution) {
		String applicationId = getApplicationId(execution);
		log.info("send notification for the application id {}", applicationId);
		// TODO send notification
		otpMailService.sendEmail("oussama.maamar@gmail.com",
				"Quotation request start at : " + DateUtil.dateTimeNow(DateUtil.MOI_DATE_ENCODING),
				"The cotation request will start", false, true);

		// TODO send SMS

	}

	protected String getApplicationId(DelegateExecution execution) {
		return (String) execution.getVariable("applicationId");
	}

}
