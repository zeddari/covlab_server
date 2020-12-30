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

    
    private Double totalVaccinesReceivedAtNupco;
    private Double totalVaccinesReceivedAtOutlets;
    private Double totalVaccinesConsumed;
    private Double wastageVaccines;
    private Double deliveryOnTimeInFull;
    private Double stockoutRatio;
    private Double warehouseFillingRate;
    private Double overallOutletPerformanceScore;
    private Double currentBalance;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdatedAt;
}
