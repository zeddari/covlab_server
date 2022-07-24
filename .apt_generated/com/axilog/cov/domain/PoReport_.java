package com.axilog.cov.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PoReport.class)
public abstract class PoReport_ {

	public static volatile SingularAttribute<PoReport, String> item;
	public static volatile SingularAttribute<PoReport, String> uom;
	public static volatile SingularAttribute<PoReport, Double> poBalanceQty;
	public static volatile SingularAttribute<PoReport, Long> etaOfDelivery;
	public static volatile SingularAttribute<PoReport, String> description;
	public static volatile SingularAttribute<PoReport, Long> id;
	public static volatile SingularAttribute<PoReport, Double> poReceivedQty;
	public static volatile SingularAttribute<PoReport, String> outlet;
	public static volatile SingularAttribute<PoReport, Double> poOriginalQty;

	public static final String ITEM = "item";
	public static final String UOM = "uom";
	public static final String PO_BALANCE_QTY = "poBalanceQty";
	public static final String ETA_OF_DELIVERY = "etaOfDelivery";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String PO_RECEIVED_QTY = "poReceivedQty";
	public static final String OUTLET = "outlet";
	public static final String PO_ORIGINAL_QTY = "poOriginalQty";

}

