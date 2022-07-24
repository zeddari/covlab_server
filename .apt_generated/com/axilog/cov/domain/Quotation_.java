package com.axilog.cov.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Quotation.class)
public abstract class Quotation_ {

	public static volatile SingularAttribute<Quotation, String> processInstanceId;
	public static volatile SingularAttribute<Quotation, String> equipName;
	public static volatile SingularAttribute<Quotation, String> requestId;
	public static volatile SingularAttribute<Quotation, Instant> dueDate;
	public static volatile SingularAttribute<Quotation, Long> id;
	public static volatile SingularAttribute<Quotation, String> assignee;
	public static volatile SingularAttribute<Quotation, Long> priority;
	public static volatile SingularAttribute<Quotation, String> taskId;

	public static final String PROCESS_INSTANCE_ID = "processInstanceId";
	public static final String EQUIP_NAME = "equipName";
	public static final String REQUEST_ID = "requestId";
	public static final String DUE_DATE = "dueDate";
	public static final String ID = "id";
	public static final String ASSIGNEE = "assignee";
	public static final String PRIORITY = "priority";
	public static final String TASK_ID = "taskId";

}

