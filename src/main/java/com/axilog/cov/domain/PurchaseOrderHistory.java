package com.axilog.cov.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A PurchaseOrder.
 */
@Entity
@Table(name = "purchase_order_history")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_no")
    private Long orderNo;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "delivered_date")
    private Date deliveredDate;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "created_at")
    private Date createdAt;
    
    @Column(name = "data")
    private byte[] data;
    
    @Column(name = "data_xlsx")
    private byte[] dataXlsx;
    
    @Column(name = "approval_time")
    private Date approvalTime;
    
    @Column(name = "approval_owner")
    private String approvalOwner;
    
    @Column(name = "approval_receiving_time")
    private Date approvalReceivingTime;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<PoStatus> poStatuses = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Outlet outlet;

    @Column(name = "hot_json")
    private String hotJson;

}
