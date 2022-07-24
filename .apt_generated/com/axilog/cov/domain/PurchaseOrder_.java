package com.axilog.cov.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PurchaseOrder.class)
public abstract class PurchaseOrder_ {

	public static volatile SingularAttribute<PurchaseOrder, String> approvalOwner;
	public static volatile SingularAttribute<PurchaseOrder, Date> approvalReceivingTime;
	public static volatile SingularAttribute<PurchaseOrder, Long> orderNo;
	public static volatile SingularAttribute<PurchaseOrder, Double> quantity;
	public static volatile SingularAttribute<PurchaseOrder, byte[]> data;
	public static volatile SingularAttribute<PurchaseOrder, byte[]> dataXlsx;
	public static volatile SingularAttribute<PurchaseOrder, Date> approvalTime;
	public static volatile SingularAttribute<PurchaseOrder, Date> createdOn;
	public static volatile SingularAttribute<PurchaseOrder, String> hotJson;
	public static volatile ListAttribute<PurchaseOrder, Product> products;
	public static volatile SingularAttribute<PurchaseOrder, Date> deliveredDate;
	public static volatile SingularAttribute<PurchaseOrder, Date> createdAt;
	public static volatile SingularAttribute<PurchaseOrder, String> createdBy;
	public static volatile SingularAttribute<PurchaseOrder, Long> id;
	public static volatile SingularAttribute<PurchaseOrder, Outlet> outlet;
	public static volatile SetAttribute<PurchaseOrder, PoStatus> poStatuses;
	public static volatile SingularAttribute<PurchaseOrder, Date> updatedAt;

	public static final String APPROVAL_OWNER = "approvalOwner";
	public static final String APPROVAL_RECEIVING_TIME = "approvalReceivingTime";
	public static final String ORDER_NO = "orderNo";
	public static final String QUANTITY = "quantity";
	public static final String DATA = "data";
	public static final String DATA_XLSX = "dataXlsx";
	public static final String APPROVAL_TIME = "approvalTime";
	public static final String CREATED_ON = "createdOn";
	public static final String HOT_JSON = "hotJson";
	public static final String PRODUCTS = "products";
	public static final String DELIVERED_DATE = "deliveredDate";
	public static final String CREATED_AT = "createdAt";
	public static final String CREATED_BY = "createdBy";
	public static final String ID = "id";
	public static final String OUTLET = "outlet";
	public static final String PO_STATUSES = "poStatuses";
	public static final String UPDATED_AT = "updatedAt";

}

