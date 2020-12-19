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

/**
 * Criteria class for the {@link com.axilog.cov.domain.StatusPO} entity. This class is used
 * in {@link com.axilog.cov.web.rest.StatusPOResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /status-pos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StatusPOCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter statusPoId;

    private StringFilter statusPoName;

    private LongFilter purchaseOrderId;

    public StatusPOCriteria() {
    }

    public StatusPOCriteria(StatusPOCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.statusPoId = other.statusPoId == null ? null : other.statusPoId.copy();
        this.statusPoName = other.statusPoName == null ? null : other.statusPoName.copy();
        this.purchaseOrderId = other.purchaseOrderId == null ? null : other.purchaseOrderId.copy();
    }

    @Override
    public StatusPOCriteria copy() {
        return new StatusPOCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getStatusPoId() {
        return statusPoId;
    }

    public void setStatusPoId(LongFilter statusPoId) {
        this.statusPoId = statusPoId;
    }

    public StringFilter getStatusPoName() {
        return statusPoName;
    }

    public void setStatusPoName(StringFilter statusPoName) {
        this.statusPoName = statusPoName;
    }

    public LongFilter getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(LongFilter purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StatusPOCriteria that = (StatusPOCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(statusPoId, that.statusPoId) &&
            Objects.equals(statusPoName, that.statusPoName) &&
            Objects.equals(purchaseOrderId, that.purchaseOrderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        statusPoId,
        statusPoName,
        purchaseOrderId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatusPOCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (statusPoId != null ? "statusPoId=" + statusPoId + ", " : "") +
                (statusPoName != null ? "statusPoName=" + statusPoName + ", " : "") +
                (purchaseOrderId != null ? "purchaseOrderId=" + purchaseOrderId + ", " : "") +
            "}";
    }

}
