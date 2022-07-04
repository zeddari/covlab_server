package com.axilog.cov.domain;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Outlet.
 */
@Entity
@Table(name = "request_quotation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestQuotation implements Serializable {

    private static final long serialVersionUID = 1L;

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_quotation_id")
    private Long requestQuotationId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "instructions")
    private String instructions;

    @Column(name = "service_location_lati")
    private Double serviceLocationLati;

    @Column(name = "service_location_long")
    private Double servicelocationlong;
    
    @Column(name = "service_request")
    private String serviceRequest;
    
    @Column(name = "service_request_type")
    private String serviceRequestType;

    @Column(name = "service_type")
    private String serviceType;
    
    @Column(name = "service_date")
    private String serviceDate;
  
  
    

}
