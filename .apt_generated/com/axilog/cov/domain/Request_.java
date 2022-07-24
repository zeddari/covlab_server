package com.axilog.cov.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Request.class)
public abstract class Request_ {

	public static volatile SingularAttribute<Request, LocalDate> createdDate;
	public static volatile SingularAttribute<Request, String> createdBy;
	public static volatile SetAttribute<Request, RequestStatus> requestStatuses;
	public static volatile SingularAttribute<Request, String> requestId;
	public static volatile SingularAttribute<Request, String> name;
	public static volatile SingularAttribute<Request, String> description;
	public static volatile SingularAttribute<Request, Long> id;
	public static volatile SingularAttribute<Request, Quotation> quotation;
	public static volatile SetAttribute<Request, Sla> slas;

	public static final String CREATED_DATE = "createdDate";
	public static final String CREATED_BY = "createdBy";
	public static final String REQUEST_STATUSES = "requestStatuses";
	public static final String REQUEST_ID = "requestId";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String QUOTATION = "quotation";
	public static final String SLAS = "slas";

}

