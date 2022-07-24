package com.axilog.cov.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RequestQuotation.class)
public abstract class RequestQuotation_ {

	public static volatile SingularAttribute<RequestQuotation, String> serviceType;
	public static volatile SingularAttribute<RequestQuotation, String> instructions;
	public static volatile SingularAttribute<RequestQuotation, String> serviceRequestType;
	public static volatile SingularAttribute<RequestQuotation, String> product;
	public static volatile SingularAttribute<RequestQuotation, Date> serviceDate;
	public static volatile SingularAttribute<RequestQuotation, String> mobileNumber;
	public static volatile SingularAttribute<RequestQuotation, Double> serviceLocationLati;
	public static volatile SingularAttribute<RequestQuotation, String> customerName;
	public static volatile SingularAttribute<RequestQuotation, String> requestQuotationId;
	public static volatile SingularAttribute<RequestQuotation, String> customerEmail;
	public static volatile SingularAttribute<RequestQuotation, String> serviceRequest;
	public static volatile SingularAttribute<RequestQuotation, Double> servicelocationlong;
	public static volatile SetAttribute<RequestQuotation, Payment> payment;
	public static volatile SingularAttribute<RequestQuotation, Long> id;

	public static final String SERVICE_TYPE = "serviceType";
	public static final String INSTRUCTIONS = "instructions";
	public static final String SERVICE_REQUEST_TYPE = "serviceRequestType";
	public static final String PRODUCT = "product";
	public static final String SERVICE_DATE = "serviceDate";
	public static final String MOBILE_NUMBER = "mobileNumber";
	public static final String SERVICE_LOCATION_LATI = "serviceLocationLati";
	public static final String CUSTOMER_NAME = "customerName";
	public static final String REQUEST_QUOTATION_ID = "requestQuotationId";
	public static final String CUSTOMER_EMAIL = "customerEmail";
	public static final String SERVICE_REQUEST = "serviceRequest";
	public static final String SERVICELOCATIONLONG = "servicelocationlong";
	public static final String PAYMENT = "payment";
	public static final String ID = "id";

}

