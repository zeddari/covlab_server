package com.axilog.cov.dto.representation;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Product.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OverallStatsRepresentation implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long currentBalance;
    private Double deliveryOnTimeInFull;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdatedAt;
    
    private Double overallOutletPerformanceScore;
    private Double stockoutRatio;
    private Long totalVaccinesReceivedAtNupco;
    private Long totalVaccinesReceivedAtOutlets;
    private Long totalVaccinesConsumed;
    private Double wastageVaccines; 
    private Double warehouseFillingRate;
    
  
    
    
}
