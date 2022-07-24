package com.axilog.cov.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Sla.class)
public abstract class Sla_ {

	public static volatile SingularAttribute<Sla, Long> duration;
	public static volatile SingularAttribute<Sla, Request> request;
	public static volatile SingularAttribute<Sla, Date> startDateTime;
	public static volatile SingularAttribute<Sla, Date> dueDateTime;
	public static volatile SingularAttribute<Sla, Boolean> outOfSla;
	public static volatile SingularAttribute<Sla, String> waiverReason;
	public static volatile SingularAttribute<Sla, String> context;
	public static volatile SingularAttribute<Sla, Long> id;
	public static volatile SingularAttribute<Sla, Date> endDateTime;
	public static volatile SingularAttribute<Sla, String> waiverRequested;

	public static final String DURATION = "duration";
	public static final String REQUEST = "request";
	public static final String START_DATE_TIME = "startDateTime";
	public static final String DUE_DATE_TIME = "dueDateTime";
	public static final String OUT_OF_SLA = "outOfSla";
	public static final String WAIVER_REASON = "waiverReason";
	public static final String CONTEXT = "context";
	public static final String ID = "id";
	public static final String END_DATE_TIME = "endDateTime";
	public static final String WAIVER_REQUESTED = "waiverRequested";

}

