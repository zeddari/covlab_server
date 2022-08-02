package com.axilog.cov.dto.command.workflow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestQuotationCommand {

    private String requestQuotationId;
    private String customerName;
    private String mobileNumber;
    private String instructions;
    private Double serviceLocationLati;
    private Double servicelocationlong;
    private String serviceRequest;
    private String serviceRequestType;
    private String serviceType;
    private String serviceDate;
    private String product;
    private Double productAmount;
    private String customerEmail;

}
