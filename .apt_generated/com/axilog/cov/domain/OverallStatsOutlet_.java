package com.axilog.cov.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OverallStatsOutlet.class)
public abstract class OverallStatsOutlet_ {

	public static volatile SingularAttribute<OverallStatsOutlet, String> outletName;
	public static volatile SingularAttribute<OverallStatsOutlet, Long> totalVaccinesReceivedAtNupco;
	public static volatile SingularAttribute<OverallStatsOutlet, Date> lastUpdatedAt;
	public static volatile SingularAttribute<OverallStatsOutlet, Double> stockoutRatio;
	public static volatile SingularAttribute<OverallStatsOutlet, Long> currentBalance;
	public static volatile SingularAttribute<OverallStatsOutlet, Long> totalVaccinesReceivedAtOutlets;
	public static volatile SingularAttribute<OverallStatsOutlet, Double> warehouseFillingRate;
	public static volatile SingularAttribute<OverallStatsOutlet, Long> totalVaccinesConsumed;
	public static volatile SingularAttribute<OverallStatsOutlet, Double> deliveryOnTimeInFull;
	public static volatile SingularAttribute<OverallStatsOutlet, Double> overallOutletPerformanceScore;
	public static volatile SingularAttribute<OverallStatsOutlet, Double> wastageVaccines;

	public static final String OUTLET_NAME = "outletName";
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

