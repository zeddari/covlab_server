package com.axilog.cov.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PoStatus.class)
public abstract class PoStatus_ {

	public static volatile SingularAttribute<PoStatus, PurchaseOrder> purchaseOrder;
	public static volatile SingularAttribute<PoStatus, Long> id;
	public static volatile SingularAttribute<PoStatus, String> status;
	public static volatile SingularAttribute<PoStatus, Date> updatedAt;

	public static final String PURCHASE_ORDER = "purchaseOrder";
	public static final String ID = "id";
	public static final String STATUS = "status";
	public static final String UPDATED_AT = "updatedAt";

}

