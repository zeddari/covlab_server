package com.axilog.cov.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Inventory.class)
public abstract class Inventory_ {

	public static volatile SingularAttribute<Inventory, Double> consumedQty;
	public static volatile SingularAttribute<Inventory, Double> damage;
	public static volatile SingularAttribute<Inventory, Double> quantitiesInTransit;
	public static volatile SingularAttribute<Inventory, String> reOrderLevel;
	public static volatile SingularAttribute<Inventory, Double> wastage;
	public static volatile SingularAttribute<Inventory, Product> product;
	public static volatile SingularAttribute<Inventory, Double> actualAvgConsumption;
	public static volatile SingularAttribute<Inventory, Date> creationTime;
	public static volatile SingularAttribute<Inventory, Date> lastUpdatedAt;
	public static volatile SingularAttribute<Inventory, Double> sample;
	public static volatile SingularAttribute<Inventory, Double> capacity;
	public static volatile SingularAttribute<Inventory, Double> receivedUserQte;
	public static volatile SingularAttribute<Inventory, String> uom;
	public static volatile SingularAttribute<Inventory, Double> receivedQty;
	public static volatile SingularAttribute<Inventory, Long> inventoryId;
	public static volatile SingularAttribute<Inventory, Double> current_balance;
	public static volatile SingularAttribute<Inventory, Double> suggestedQuantity;
	public static volatile SingularAttribute<Inventory, Long> id;
	public static volatile SingularAttribute<Inventory, Boolean> isLastInstance;
	public static volatile SingularAttribute<Inventory, Outlet> outlet;
	public static volatile SingularAttribute<Inventory, Double> consumedUserQte;
	public static volatile SingularAttribute<Inventory, String> status;

	public static final String CONSUMED_QTY = "consumedQty";
	public static final String DAMAGE = "damage";
	public static final String QUANTITIES_IN_TRANSIT = "quantitiesInTransit";
	public static final String RE_ORDER_LEVEL = "reOrderLevel";
	public static final String WASTAGE = "wastage";
	public static final String PRODUCT = "product";
	public static final String ACTUAL_AVG_CONSUMPTION = "actualAvgConsumption";
	public static final String CREATION_TIME = "creationTime";
	public static final String LAST_UPDATED_AT = "lastUpdatedAt";
	public static final String SAMPLE = "sample";
	public static final String CAPACITY = "capacity";
	public static final String RECEIVED_USER_QTE = "receivedUserQte";
	public static final String UOM = "uom";
	public static final String RECEIVED_QTY = "receivedQty";
	public static final String INVENTORY_ID = "inventoryId";
	public static final String CURRENT_BALANCE = "current_balance";
	public static final String SUGGESTED_QUANTITY = "suggestedQuantity";
	public static final String ID = "id";
	public static final String IS_LAST_INSTANCE = "isLastInstance";
	public static final String OUTLET = "outlet";
	public static final String CONSUMED_USER_QTE = "consumedUserQte";
	public static final String STATUS = "status";

}

