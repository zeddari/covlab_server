package com.axilog.cov.service.activity.delegate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.axilog.cov.domain.Product;
import com.axilog.cov.service.ProductService;
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

    @Autowired
    ProductService productService;

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
        String containerProductCode = (String) execution.getVariable("requestedProductCode");
        List<Product> products = productService.findByProductCode(containerProductCode);
        if (Optional.ofNullable(products).isPresent() && !products.isEmpty()) {
            Set<Inventory> inventories =	products.get(0).getInventories();
            if (Optional.ofNullable(inventories).isPresent() && !inventories.isEmpty()) {
                List<Inventory> inventoriesActive = inventories.stream().filter(inv -> inv.getIsLastInstance().equals(Boolean.TRUE)).collect(Collectors.toList());
                if (Optional.ofNullable(inventoriesActive).isPresent() && !inventoriesActive.isEmpty())
                    execution.setVariable(WorkflowVariables.WAITING_ROOM_ACTION , "ContainerExist");
            }
        }

	}

	protected String getApplicationId(DelegateExecution execution) {
		return (String) execution.getVariable("applicationId");
	}

}
