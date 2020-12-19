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
 * Criteria class for the {@link com.axilog.cov.domain.Tickets} entity. This class is used
 * in {@link com.axilog.cov.web.rest.TicketsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tickets?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TicketsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter ticketNo;

    private StringFilter ticketType;

    private StringFilter ticketStatus;

    private LocalDateFilter ticketDueDate;

    private StringFilter ticketPriority;

    private LocalDateFilter ticketCreatedOn;

    private LocalDateFilter ticketUpdateAt;

    private LongFilter outletId;

    private LongFilter productId;

    public TicketsCriteria() {
    }

    public TicketsCriteria(TicketsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.ticketNo = other.ticketNo == null ? null : other.ticketNo.copy();
        this.ticketType = other.ticketType == null ? null : other.ticketType.copy();
        this.ticketStatus = other.ticketStatus == null ? null : other.ticketStatus.copy();
        this.ticketDueDate = other.ticketDueDate == null ? null : other.ticketDueDate.copy();
        this.ticketPriority = other.ticketPriority == null ? null : other.ticketPriority.copy();
        this.ticketCreatedOn = other.ticketCreatedOn == null ? null : other.ticketCreatedOn.copy();
        this.ticketUpdateAt = other.ticketUpdateAt == null ? null : other.ticketUpdateAt.copy();
        this.outletId = other.outletId == null ? null : other.outletId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
    }

    @Override
    public TicketsCriteria copy() {
        return new TicketsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(LongFilter ticketNo) {
        this.ticketNo = ticketNo;
    }

    public StringFilter getTicketType() {
        return ticketType;
    }

    public void setTicketType(StringFilter ticketType) {
        this.ticketType = ticketType;
    }

    public StringFilter getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(StringFilter ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public LocalDateFilter getTicketDueDate() {
        return ticketDueDate;
    }

    public void setTicketDueDate(LocalDateFilter ticketDueDate) {
        this.ticketDueDate = ticketDueDate;
    }

    public StringFilter getTicketPriority() {
        return ticketPriority;
    }

    public void setTicketPriority(StringFilter ticketPriority) {
        this.ticketPriority = ticketPriority;
    }

    public LocalDateFilter getTicketCreatedOn() {
        return ticketCreatedOn;
    }

    public void setTicketCreatedOn(LocalDateFilter ticketCreatedOn) {
        this.ticketCreatedOn = ticketCreatedOn;
    }

    public LocalDateFilter getTicketUpdateAt() {
        return ticketUpdateAt;
    }

    public void setTicketUpdateAt(LocalDateFilter ticketUpdateAt) {
        this.ticketUpdateAt = ticketUpdateAt;
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
        final TicketsCriteria that = (TicketsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(ticketNo, that.ticketNo) &&
            Objects.equals(ticketType, that.ticketType) &&
            Objects.equals(ticketStatus, that.ticketStatus) &&
            Objects.equals(ticketDueDate, that.ticketDueDate) &&
            Objects.equals(ticketPriority, that.ticketPriority) &&
            Objects.equals(ticketCreatedOn, that.ticketCreatedOn) &&
            Objects.equals(ticketUpdateAt, that.ticketUpdateAt) &&
            Objects.equals(outletId, that.outletId) &&
            Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        ticketNo,
        ticketType,
        ticketStatus,
        ticketDueDate,
        ticketPriority,
        ticketCreatedOn,
        ticketUpdateAt,
        outletId,
        productId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TicketsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (ticketNo != null ? "ticketNo=" + ticketNo + ", " : "") +
                (ticketType != null ? "ticketType=" + ticketType + ", " : "") +
                (ticketStatus != null ? "ticketStatus=" + ticketStatus + ", " : "") +
                (ticketDueDate != null ? "ticketDueDate=" + ticketDueDate + ", " : "") +
                (ticketPriority != null ? "ticketPriority=" + ticketPriority + ", " : "") +
                (ticketCreatedOn != null ? "ticketCreatedOn=" + ticketCreatedOn + ", " : "") +
                (ticketUpdateAt != null ? "ticketUpdateAt=" + ticketUpdateAt + ", " : "") +
                (outletId != null ? "outletId=" + outletId + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
            "}";
    }

}
