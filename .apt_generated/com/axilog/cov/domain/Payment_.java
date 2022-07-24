package com.axilog.cov.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Payment.class)
public abstract class Payment_ {

	public static volatile SingularAttribute<Payment, String> supervisorName;
	public static volatile SingularAttribute<Payment, String> reason;
	public static volatile SingularAttribute<Payment, byte[]> invoiceFile;
	public static volatile SingularAttribute<Payment, Date> createdDate;
	public static volatile SingularAttribute<Payment, RequestQuotation> requestQuotation;
	public static volatile SingularAttribute<Payment, String> driverName;
	public static volatile SingularAttribute<Payment, String> invoiceId;
	public static volatile SingularAttribute<Payment, Long> id;
	public static volatile SingularAttribute<Payment, String> motif;
	public static volatile SingularAttribute<Payment, Double> paymentAmount;
	public static volatile SingularAttribute<Payment, String> taskId;

	public static final String SUPERVISOR_NAME = "supervisorName";
	public static final String REASON = "reason";
	public static final String INVOICE_FILE = "invoiceFile";
	public static final String CREATED_DATE = "createdDate";
	public static final String REQUEST_QUOTATION = "requestQuotation";
	public static final String DRIVER_NAME = "driverName";
	public static final String INVOICE_ID = "invoiceId";
	public static final String ID = "id";
	public static final String MOTIF = "motif";
	public static final String PAYMENT_AMOUNT = "paymentAmount";
	public static final String TASK_ID = "taskId";

}

