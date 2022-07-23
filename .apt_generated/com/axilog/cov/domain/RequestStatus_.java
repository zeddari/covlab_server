package com.axilog.cov.domain;

import com.axilog.cov.enums.RequestStatusEnum;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RequestStatus.class)
public abstract class RequestStatus_ {

	public static volatile SingularAttribute<RequestStatus, Request> request;
	public static volatile SingularAttribute<RequestStatus, Long> id;
	public static volatile SingularAttribute<RequestStatus, RequestStatusEnum> status;
	public static volatile SingularAttribute<RequestStatus, LocalDate> updatedAt;

	public static final String REQUEST = "request";
	public static final String ID = "id";
	public static final String STATUS = "status";
	public static final String UPDATED_AT = "updatedAt";

}

