package com.axilog.cov.dto.representation;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PurchaseOrderDetail {

	 private Long id; 
	 private Long poNo;
	 private Double quantity;
	 private String createdBy;
	 private String approvalOwner;
	 
	 @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	 private Date createdOn;	
	 
	 @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	 private Date deliveredDate;
	 
	 @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	 private Date updatedAt;
	 
	 @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	 private Date createdAt;
	 
	 @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	 private Date approvalTime;
	 
	 @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	 private Date approvalReceivingTime;
	 
	 private String outlet;
	 private String status;
	 private String codeProduct;
	 private String descPro;
	 
	 private String data;
	
}
