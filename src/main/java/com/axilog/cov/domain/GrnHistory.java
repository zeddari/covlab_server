package com.axilog.cov.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A GrnHistory.
 */
@Entity
@Table(name = "grn_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrnHistory implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "grn_number")
	 private String grnNumber;
    
    @Column(name = "created_at")
    private Date createdAt;
	  
	@Column(name = "created_by")
	 private String createdBy;
	
	@Column(name = "status")
    private String status;

	@Column(name = "order_no")
    private String orderNo;
	
	@Column(name = "product_code")
    private String productCode;
	
	@Column(name = "subs_code")
    private String subsCode;
	
	@Column(name = "subs_description")
    private String subsDescription;
	
	@Column(name = "subs_category")
    private String subsCategory;
	
	@Column(name = "description")
    private String description;
	
	@Column(name = "category")
    private String category;
		
	@Column(name = "uom")
	 private String uom;
   
	@Column(name = "poQuantity")
    private Double poQuantity;

    @Column(name = "received")
    private Double received;

    @Column(name = "outlet_name")
    private String outletName;

   
    
}
