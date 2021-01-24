package com.axilog.cov.dto.representation;

import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GrnHistoryDetail {

	 
	private  Long id;
	
	private String grnNumber;
   
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	 private Date createdAt;
	  
	private String createdBy;
	
   private String status;

   private String productCode;
	
   private String description;
	
   private String category;
		
   private String uom;
  
   private Double poQuantity;

   private Double received;

   private String outletName;
	 
	 
	
}
