package com.axilog.cov.domain;

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

@Entity
@Table(name = "po_report")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PoReport {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "item")
    private String  item;
	
	@Column(name = "description")
    private String  description;
	
	@Column(name = "uom")
    private String  uom;
	
	@Column(name = "po_original_quantity")
    private Double  poOriginalQty;
	
	@Column(name = "po_received_quantity")
    private Double  poReceivedQty;
	
	@Column(name = "po_balance_quantity")
    private Double  poBalanceQty;
	
	@Column(name = "eta_Delivery")
    private Long 	etaOfDelivery;
	
	@Column(name = "outlet")
    private String  outlet;
	
}
