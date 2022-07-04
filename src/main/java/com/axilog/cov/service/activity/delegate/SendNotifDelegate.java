package com.axilog.cov.service.activity.delegate;

import java.util.Arrays;
import java.util.List;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axilog.cov.constant.WorkflowVariables;
import com.axilog.cov.domain.Inventory;
import com.axilog.cov.service.InventoryService;
import com.axilog.cov.service.OtpMailService;
import com.axilog.cov.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SendNotifDelegate implements JavaDelegate {

	@Autowired
	OtpMailService otpMailService;
	@Autowired
	InventoryService inventoryService;

	@Override
	public void execute(DelegateExecution execution) {
		String applicationId = getApplicationId(execution);
		log.info("send notification for the application id {}", applicationId);
		
		String destintationEmail = (String) execution.getVariable("emailDestination");
		// TODO send notification
		otpMailService.sendEmail(destintationEmail,
				"Quotation request start at : " + DateUtil.dateTimeNow(DateUtil.MOI_DATE_ENCODING),
				"The cotation request will start", false, true);

		// TODO send SMS
		
		
		//how to decide if container Exist
		//select inventory where status available.
		List<Inventory> inventory =	inventoryService.findByStatusIn(Arrays.asList("available"));
		if (inventory != null && inventory.size()!=0) {
			execution.setVariable(WorkflowVariables.WAITING_ROOM_ACTION , "ContainerExist");
		}
	}

	protected String getApplicationId(DelegateExecution execution) {
		return (String) execution.getVariable("applicationId");
	}

}
