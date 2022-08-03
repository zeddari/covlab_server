package com.axilog.cov.dto.command.workflow;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StartQuotationRequestProcessCommand {
	@NotBlank
	private String applicationId;
	private String emailDestination;
    private String requestedProductCode;
    private String serviceType;
    private String requestQuotationId;
    private String serviceLocationLong;
    private String serviceLocationLati;
    private String productAmount;
    private String supervisorId;


    
}
