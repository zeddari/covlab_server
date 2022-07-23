package com.axilog.cov.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Tasks.class)
public abstract class Tasks_ {

	public static volatile SingularAttribute<Tasks, String> processInstanceId;
	public static volatile SingularAttribute<Tasks, String> equipName;
	public static volatile SingularAttribute<Tasks, String> requestId;
	public static volatile SingularAttribute<Tasks, Instant> dueDate;
	public static volatile SingularAttribute<Tasks, Long> id;
	public static volatile SingularAttribute<Tasks, String> assignee;
	public static volatile SingularAttribute<Tasks, Long> priority;
	public static volatile SingularAttribute<Tasks, String> taskId;

	public static final String PROCESS_INSTANCE_ID = "processInstanceId";
	public static final String EQUIP_NAME = "equipName";
	public static final String REQUEST_ID = "requestId";
	public static final String DUE_DATE = "dueDate";
	public static final String ID = "id";
	public static final String ASSIGNEE = "assignee";
	public static final String PRIORITY = "priority";
	public static final String TASK_ID = "taskId";

}

