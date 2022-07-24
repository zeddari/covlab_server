package com.axilog.cov.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Outlet.class)
public abstract class Outlet_ {

	public static volatile SingularAttribute<Outlet, Double> outletLng;
	public static volatile SetAttribute<Outlet, Tickets> tickets;
	public static volatile SingularAttribute<Outlet, String> outletParentRegion;
	public static volatile SingularAttribute<Outlet, Long> outletId;
	public static volatile SingularAttribute<Outlet, String> outletRegion;
	public static volatile SingularAttribute<Outlet, Double> outletLat;
	public static volatile SingularAttribute<Outlet, String> outletType;
	public static volatile SingularAttribute<Outlet, String> outletName;
	public static volatile SetAttribute<Outlet, Inventory> inventories;
	public static volatile SingularAttribute<Outlet, Long> id;
	public static volatile SetAttribute<Outlet, DeviceOverviewStats> deviceOverviewStats;
	public static volatile SetAttribute<Outlet, PurchaseOrder> purchaseOrders;
	public static volatile SingularAttribute<Outlet, String> outletAdress;

	public static final String OUTLET_LNG = "outletLng";
	public static final String TICKETS = "tickets";
	public static final String OUTLET_PARENT_REGION = "outletParentRegion";
	public static final String OUTLET_ID = "outletId";
	public static final String OUTLET_REGION = "outletRegion";
	public static final String OUTLET_LAT = "outletLat";
	public static final String OUTLET_TYPE = "outletType";
	public static final String OUTLET_NAME = "outletName";
	public static final String INVENTORIES = "inventories";
	public static final String ID = "id";
	public static final String DEVICE_OVERVIEW_STATS = "deviceOverviewStats";
	public static final String PURCHASE_ORDERS = "purchaseOrders";
	public static final String OUTLET_ADRESS = "outletAdress";

}

