package com.axilog.cov.dto.representation;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PurchaseOrderDetail {

	private float PoNo;
	 private Double quantityPo;
	 private String createdByPo;
	 private LocalDate createdOnPO;	
	 private LocalDate deliveredDatePo;
	 private LocalDate updatedAtPo;
	 private LocalDate createdAtPo;
	 private String region;
	 private String statusPo;
	 private String codeProductPo;
	 private String descProPo;
	
}
