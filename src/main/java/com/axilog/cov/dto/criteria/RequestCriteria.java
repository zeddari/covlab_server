package com.axilog.cov.dto.criteria;

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
 * Criteria class for the {@link com.axilog.myflow.domain.Request} entity. This class is used
 * in {@link com.axilog.myflow.web.rest.RequestResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /requests?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RequestCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter createdDate;

    private StringFilter createdBy;

    private StringFilter name;

    private StringFilter description;

    private StringFilter requestId;

    private LongFilter requestStatusId;

    private LongFilter slaId;

    private LongFilter equipmentId;

    public RequestCriteria() {
    }

    public RequestCriteria(RequestCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.requestId = other.requestId == null ? null : other.requestId.copy();
        this.requestStatusId = other.requestStatusId == null ? null : other.requestStatusId.copy();
        this.slaId = other.slaId == null ? null : other.slaId.copy();
        this.equipmentId = other.equipmentId == null ? null : other.equipmentId.copy();
    }

    @Override
    public RequestCriteria copy() {
        return new RequestCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateFilter createdDate) {
        this.createdDate = createdDate;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getRequestId() {
        return requestId;
    }

    public void setRequestId(StringFilter requestId) {
        this.requestId = requestId;
    }

    public LongFilter getRequestStatusId() {
        return requestStatusId;
    }

    public void setRequestStatusId(LongFilter requestStatusId) {
        this.requestStatusId = requestStatusId;
    }

    public LongFilter getSlaId() {
        return slaId;
    }

    public void setSlaId(LongFilter slaId) {
        this.slaId = slaId;
    }

    public LongFilter getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(LongFilter equipmentId) {
        this.equipmentId = equipmentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RequestCriteria that = (RequestCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(requestId, that.requestId) &&
            Objects.equals(requestStatusId, that.requestStatusId) &&
            Objects.equals(slaId, that.slaId) &&
            Objects.equals(equipmentId, that.equipmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        createdDate,
        createdBy,
        name,
        description,
        requestId,
        requestStatusId,
        slaId,
        equipmentId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (requestId != null ? "requestId=" + requestId + ", " : "") +
                (requestStatusId != null ? "requestStatusId=" + requestStatusId + ", " : "") +
                (slaId != null ? "slaId=" + slaId + ", " : "") +
                (equipmentId != null ? "equipmentId=" + equipmentId + ", " : "") +
            "}";
    }

}
