package com.axilog.cov.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Product.
 */
@Entity
@Table(name = "outlet_overview_stats")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OverallStatsOutlet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "outlet_name")
    private String outletName;
    
    @Column(name = "total_vaccines_received_at_nupco")
    private Long totalVaccinesReceivedAtNupco;
    
    @Column(name = "total_vaccines_received_at_outlets")
    private Long totalVaccinesReceivedAtOutlets;
    
    @Column(name = "total_vaccines_consumed")
    private Long totalVaccinesConsumed;
    
    @Column(name = "wastage_vaccines")
    private Double wastageVaccines;
    
    @Column(name = "delivery_on_time_in_full")
    private Double deliveryOnTimeInFull;
    
    @Column(name = "stockout_ratio")
    private Double stockoutRatio;
    
    @Column(name = "warehouse_filling_rate")
    private Double warehouseFillingRate;
    
    @Column(name = "overall_outlet_performance_score")
    private Double overallOutletPerformanceScore;
    
    @Column(name = "current_balance")
    private Long currentBalance;
    
    @Column(name = "last_updated_at")
    @Id
    private Date lastUpdatedAt;
    
}
