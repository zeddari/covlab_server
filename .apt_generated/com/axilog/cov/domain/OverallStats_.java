package com.axilog.cov.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OverallStats.class)
public abstract class OverallStats_ {

	public static volatile SingularAttribute<OverallStats, Long> totalVaccinesReceivedAtNupco;
	public static volatile SingularAttribute<OverallStats, Date> lastUpdatedAt;
	public static volatile SingularAttribute<OverallStats, Double> stockoutRatio;
	public static volatile SingularAttribute<OverallStats, Long> currentBalance;
	public static volatile SingularAttribute<OverallStats, Long> totalVaccinesReceivedAtOutlets;
	public static volatile SingularAttribute<OverallStats, Double> warehouseFillingRate;
	public static volatile SingularAttribute<OverallStats, Long> totalVaccinesConsumed;
	public static volatile SingularAttribute<OverallStats, Double> deliveryOnTimeInFull;
	public static volatile SingularAttribute<OverallStats, Double> overallOutletPerformanceScore;
	public static volatile SingularAttribute<OverallStats, Double> wastageVaccines;

	public static final String TOTAL_VACCINES_RECEIVED_AT_NUPCO = "totalVaccinesReceivedAtNupco";
	public static final String LAST_UPDATED_AT = "lastUpdatedAt";
	public static final String STOCKOUT_RATIO = "stockoutRatio";
	public static final String CURRENT_BALANCE = "currentBalance";
	public static final String TOTAL_VACCINES_RECEIVED_AT_OUTLETS = "totalVaccinesReceivedAtOutlets";
	public static final String WAREHOUSE_FILLING_RATE = "warehouseFillingRate";
	public static final String TOTAL_VACCINES_CONSUMED = "totalVaccinesConsumed";
	public static final String DELIVERY_ON_TIME_IN_FULL = "deliveryOnTimeInFull";
	public static final String OVERALL_OUTLET_PERFORMANCE_SCORE = "overallOutletPerformanceScore";
	public static final String WASTAGE_VACCINES = "wastageVaccines";

}

