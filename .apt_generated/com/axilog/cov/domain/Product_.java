package com.axilog.cov.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Product.class)
public abstract class Product_ {

	public static volatile SingularAttribute<Product, String> productCode;
	public static volatile SetAttribute<Product, Tickets> tickets;
	public static volatile SingularAttribute<Product, Long> productId;
	public static volatile SetAttribute<Product, Inventory> inventories;
	public static volatile SetAttribute<Product, Substitute> substitutes;
	public static volatile SingularAttribute<Product, String> description;
	public static volatile SingularAttribute<Product, Long> id;
	public static volatile SingularAttribute<Product, DeviceOverviewStats> deviceOverviewStats;
	public static volatile SingularAttribute<Product, Category> category;
	public static volatile SetAttribute<Product, PurchaseOrder> purchaseOrders;

	public static final String PRODUCT_CODE = "productCode";
	public static final String TICKETS = "tickets";
	public static final String PRODUCT_ID = "productId";
	public static final String INVENTORIES = "inventories";
	public static final String SUBSTITUTES = "substitutes";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String DEVICE_OVERVIEW_STATS = "deviceOverviewStats";
	public static final String CATEGORY = "category";
	public static final String PURCHASE_ORDERS = "purchaseOrders";

}

