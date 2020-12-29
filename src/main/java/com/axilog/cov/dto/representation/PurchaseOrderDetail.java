package com.axilog.cov.dto.representation;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PurchaseOrderDetail {

	private float PoNo;
	 private Double quantityPo;
	 private String createdByPo;
	 private Date createdOnPO;	
	 private Date deliveredDatePo;
	 private Date updatedAtPo;
	 private Date createdAtPo;
	 private String region;
	 private String statusPo;
	 private String codeProductPo;
	 private String descProPo;
	
}
