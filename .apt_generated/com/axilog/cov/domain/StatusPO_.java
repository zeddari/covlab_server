package com.axilog.cov.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StatusPO.class)
public abstract class StatusPO_ {

	public static volatile SingularAttribute<StatusPO, String> statusPoName;
	public static volatile SingularAttribute<StatusPO, PurchaseOrder> purchaseOrder;
	public static volatile SingularAttribute<StatusPO, Long> statusPoId;
	public static volatile SingularAttribute<StatusPO, Long> id;

	public static final String STATUS_PO_NAME = "statusPoName";
	public static final String PURCHASE_ORDER = "purchaseOrder";
	public static final String STATUS_PO_ID = "statusPoId";
	public static final String ID = "id";

}

