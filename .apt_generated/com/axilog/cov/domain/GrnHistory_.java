package com.axilog.cov.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(GrnHistory.class)
public abstract class GrnHistory_ {

	public static volatile SingularAttribute<GrnHistory, String> orderNo;
	public static volatile SingularAttribute<GrnHistory, String> description;
	public static volatile SingularAttribute<GrnHistory, Double> received;
	public static volatile SingularAttribute<GrnHistory, String> subsDescription;
	public static volatile SingularAttribute<GrnHistory, String> subsCode;
	public static volatile SingularAttribute<GrnHistory, Date> createdAt;
	public static volatile SingularAttribute<GrnHistory, String> outletName;
	public static volatile SingularAttribute<GrnHistory, String> uom;
	public static volatile SingularAttribute<GrnHistory, String> productCode;
	public static volatile SingularAttribute<GrnHistory, String> grnNumber;
	public static volatile SingularAttribute<GrnHistory, String> createdBy;
	public static volatile SingularAttribute<GrnHistory, String> subsCategory;
	public static volatile SingularAttribute<GrnHistory, Double> poQuantity;
	public static volatile SingularAttribute<GrnHistory, Long> id;
	public static volatile SingularAttribute<GrnHistory, String> category;
	public static volatile SingularAttribute<GrnHistory, String> status;

	public static final String ORDER_NO = "orderNo";
	public static final String DESCRIPTION = "description";
	public static final String RECEIVED = "received";
	public static final String SUBS_DESCRIPTION = "subsDescription";
	public static final String SUBS_CODE = "subsCode";
	public static final String CREATED_AT = "createdAt";
	public static final String OUTLET_NAME = "outletName";
	public static final String UOM = "uom";
	public static final String PRODUCT_CODE = "productCode";
	public static final String GRN_NUMBER = "grnNumber";
	public static final String CREATED_BY = "createdBy";
	public static final String SUBS_CATEGORY = "subsCategory";
	public static final String PO_QUANTITY = "poQuantity";
	public static final String ID = "id";
	public static final String CATEGORY = "category";
	public static final String STATUS = "status";

}

