package com.axilog.cov.dto.mapper;

import com.axilog.cov.domain.RequestQuotation;
import com.axilog.cov.dto.command.workflow.RequestQuotationCommand;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class QuotationMapper {

    public static final String DATE_FORMAT = "yyyy-mm-dd";
    public static RequestQuotation toQuotation(RequestQuotationCommand requestQuotationCommand) throws ParseException {
        return RequestQuotation.builder()
            .customerName(requestQuotationCommand.getCustomerName())
            .product(requestQuotationCommand.getProduct())
            .requestQuotationId(requestQuotationCommand.getRequestQuotationId())
            .instructions(requestQuotationCommand.getInstructions())
            .mobileNumber(requestQuotationCommand.getMobileNumber())
            .serviceLocationLati(requestQuotationCommand.getServiceLocationLati())
            .servicelocationlong(requestQuotationCommand.getServicelocationlong())
            .serviceRequest(requestQuotationCommand.getServiceRequest())
            .serviceRequestType(requestQuotationCommand.getServiceRequestType())
            .serviceType(requestQuotationCommand.getServiceType())
            .serviceDate(new SimpleDateFormat(DATE_FORMAT).parse(requestQuotationCommand.getServiceDate()))
            .build();
    }
}
