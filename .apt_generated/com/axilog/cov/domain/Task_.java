package com.axilog.cov.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Task.class)
public abstract class Task_ {

	public static volatile SingularAttribute<Task, String> processInstanceId;
	public static volatile SingularAttribute<Task, String> equipName;
	public static volatile SingularAttribute<Task, String> requestId;
	public static volatile SingularAttribute<Task, Instant> dueDate;
	public static volatile SingularAttribute<Task, Long> id;
	public static volatile SingularAttribute<Task, String> assignee;
	public static volatile SingularAttribute<Task, Long> priority;
	public static volatile SingularAttribute<Task, String> taskId;

	public static final String PROCESS_INSTANCE_ID = "processInstanceId";
	public static final String EQUIP_NAME = "equipName";
	public static final String REQUEST_ID = "requestId";
	public static final String DUE_DATE = "dueDate";
	public static final String ID = "id";
	public static final String ASSIGNEE = "assignee";
	public static final String PRIORITY = "priority";
	public static final String TASK_ID = "taskId";

}

