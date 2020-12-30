package com.axilog.cov.dto.representation;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PurchaseOrderDetail {

	private float PoNo;
	 private Double quantity;
	 private String createdBy;
	 private Date createdOn;	
	 private Date deliveredDate;
	 private Date updatedAt;
	 private Date createdAt;
	 private String outlet;
	 private String status;
	 private String codeProduct;
	 private String descPro;
	 private byte[] data;
	
}
