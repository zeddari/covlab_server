package com.axilog.cov.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.axilog.cov.domain.PurchaseOrder} entity. This class is used
 * in {@link com.axilog.cov.web.rest.PurchaseOrderResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /purchase-orders?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PurchaseOrderCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter orderNo;

    private DoubleFilter quantity;

    private StringFilter createdBy;

    private LocalDateFilter createdOn;

    private LocalDateFilter deliveredDate;

    private LocalDateFilter updatedAt;

    private LocalDateFilter createdAt;

    private LongFilter poStatusId;

    private LongFilter outletId;

    private LongFilter productId;

    public PurchaseOrderCriteria() {
    }

    public PurchaseOrderCriteria(PurchaseOrderCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.orderNo = other.orderNo == null ? null : other.orderNo.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.deliveredDate = other.deliveredDate == null ? null : other.deliveredDate.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.poStatusId = other.poStatusId == null ? null : other.poStatusId.copy();
        this.outletId = other.outletId == null ? null : other.outletId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
    }

    @Override
    public PurchaseOrderCriteria copy() {
        return new PurchaseOrderCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(LongFilter orderNo) {
        this.orderNo = orderNo;
    }

    public DoubleFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(DoubleFilter quantity) {
        this.quantity = quantity;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateFilter getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateFilter createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateFilter getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(LocalDateFilter deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public LocalDateFilter getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateFilter updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateFilter getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateFilter createdAt) {
        this.createdAt = createdAt;
    }

    public LongFilter getPoStatusId() {
        return poStatusId;
    }

    public void setPoStatusId(LongFilter poStatusId) {
        this.poStatusId = poStatusId;
    }

    public LongFilter getOutletId() {
        return outletId;
    }

    public void setOutletId(LongFilter outletId) {
        this.outletId = outletId;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PurchaseOrderCriteria that = (PurchaseOrderCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(orderNo, that.orderNo) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(deliveredDate, that.deliveredDate) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(poStatusId, that.poStatusId) &&
            Objects.equals(outletId, that.outletId) &&
            Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        orderNo,
        quantity,
        createdBy,
        createdOn,
        deliveredDate,
        updatedAt,
        createdAt,
        poStatusId,
        outletId,
        productId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PurchaseOrderCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (orderNo != null ? "orderNo=" + orderNo + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
                (deliveredDate != null ? "deliveredDate=" + deliveredDate + ", " : "") +
                (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (poStatusId != null ? "poStatusId=" + poStatusId + ", " : "") +
                (outletId != null ? "outletId=" + outletId + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
            "}";
    }

}
