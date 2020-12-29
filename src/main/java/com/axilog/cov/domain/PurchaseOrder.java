package com.axilog.cov.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    private LocalDate createdOn;

    @Column(name = "delivered_date")
    private LocalDate deliveredDate;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @OneToMany(mappedBy = "purchaseOrder")
    private Set<PoStatus> poStatuses = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "purchaseOrders", allowSetters = true)
    private Outlet outlet;

    @ManyToOne
    @JsonIgnoreProperties(value = "purchaseOrders", allowSetters = true)
    private Product product;

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

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public PurchaseOrder createdOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDate getDeliveredDate() {
        return deliveredDate;
    }

    public PurchaseOrder deliveredDate(LocalDate deliveredDate) {
        this.deliveredDate = deliveredDate;
        return this;
    }

    public void setDeliveredDate(LocalDate deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public PurchaseOrder updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public PurchaseOrder createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Set<PoStatus> getPoStatuses() {
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

    public Product getProduct() {
        return product;
    }

    public PurchaseOrder product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
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
