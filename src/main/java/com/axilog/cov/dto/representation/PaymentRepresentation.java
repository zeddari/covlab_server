package com.axilog.cov.dto.representation;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Product.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRepresentation implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long paymentId;
    private String customerName;
    private String invoiceId;
    private String customerMobileNumber;
    private String customerEmail;
    private String serviceType;
    private String driverName;
    private String supervisorName;
    private Double paymentAmount;

  
    
    
}
