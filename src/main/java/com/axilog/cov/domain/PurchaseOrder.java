package com.axilog.cov.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.axilog.cov.domain.converter.JsonToMapConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A PurchaseOrder.
 */
@Entity
@Table(name = "purchase_order")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder implements Serializable {

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

    @OneToMany(fetch = FetchType.EAGER)
    private Set<PoStatus> poStatuses = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Outlet outlet;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Product> products;

    @Column(name = "hot_json")
    private String hotJson;

    
//    @Column(name = "hot_json", columnDefinition = "json")
//    @Convert(attributeName = "data", converter = JsonToMapConverter.class)
//    private Map<String, Object> hotJson = new HashMap<>();
    
    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public PurchaseOrder orderNo(Long orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Double getQuantity() {
        return quantity;
    }

    public PurchaseOrder quantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public PurchaseOrder createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }


    public Set<PoStatus> getPoStatuses() {
    	if (poStatuses == null) poStatuses = new HashSet<>();
        return poStatuses;
    }

    public PurchaseOrder poStatuses(Set<PoStatus> poStatuses) {
        this.poStatuses = poStatuses;
        return this;
    }

    public PurchaseOrder addPoStatus(PoStatus poStatus) {
        this.poStatuses.add(poStatus);
        poStatus.setPurchaseOrder(this);
        return this;
    }

    public PurchaseOrder removePoStatus(PoStatus poStatus) {
        this.poStatuses.remove(poStatus);
        poStatus.setPurchaseOrder(null);
        return this;
    }

    public void setPoStatuses(Set<PoStatus> poStatuses) {
        this.poStatuses = poStatuses;
    }

    public Outlet getOutlet() {
        return outlet;
    }

    public PurchaseOrder outlet(Outlet outlet) {
        this.outlet = outlet;
        return this;
    }

    public void setOutlet(Outlet outlet) {
        this.outlet = outlet;
    }

    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PurchaseOrder)) {
            return false;
        }
        return id != null && id.equals(((PurchaseOrder) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PurchaseOrder{" +
            "id=" + getId() +
            ", orderNo=" + getOrderNo() +
            ", quantity=" + getQuantity() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", deliveredDate='" + getDeliveredDate() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
