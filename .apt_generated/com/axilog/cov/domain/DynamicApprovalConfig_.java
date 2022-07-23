package com.axilog.cov.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DynamicApprovalConfig.class)
public abstract class DynamicApprovalConfig_ {

	public static volatile SingularAttribute<DynamicApprovalConfig, String> currentStep;
	public static volatile SingularAttribute<DynamicApprovalConfig, Boolean> finalStatus;
	public static volatile SingularAttribute<DynamicApprovalConfig, String> currentStepStatus;
	public static volatile SingularAttribute<DynamicApprovalConfig, String> nextStep;
	public static volatile SingularAttribute<DynamicApprovalConfig, Boolean> startStatus;
	public static volatile SingularAttribute<DynamicApprovalConfig, Long> id;
	public static volatile SingularAttribute<DynamicApprovalConfig, String> nextStepStatus;
	public static volatile SingularAttribute<DynamicApprovalConfig, String> outlet;
	public static volatile SingularAttribute<DynamicApprovalConfig, String> currentStepEmail;
	public static volatile SingularAttribute<DynamicApprovalConfig, String> currentStepEmailcc;

	public static final String CURRENT_STEP = "currentStep";
	public static final String FINAL_STATUS = "finalStatus";
	public static final String CURRENT_STEP_STATUS = "currentStepStatus";
	public static final String NEXT_STEP = "nextStep";
	public static final String START_STATUS = "startStatus";
	public static final String ID = "id";
	public static final String NEXT_STEP_STATUS = "nextStepStatus";
	public static final String OUTLET = "outlet";
	public static final String CURRENT_STEP_EMAIL = "currentStepEmail";
	public static final String CURRENT_STEP_EMAILCC = "currentStepEmailcc";

}

