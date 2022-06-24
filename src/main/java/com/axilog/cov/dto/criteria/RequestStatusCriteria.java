package com.axilog.cov.dto.criteria;

import java.io.Serializable;
import java.util.Objects;

import com.axilog.cov.enums.RequestStatusEnum;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;

/**
 * Criteria class for the {@link com.axilog.myflow.domain.RequestStatus} entity. This class is used
 * in {@link com.axilog.myflow.web.rest.RequestStatusResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /request-statuses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RequestStatusCriteria implements Serializable, Criteria {
    /**
     * Class for filtering RequestStatusEnum
     */
    public static class RequestStatusEnumFilter extends Filter<RequestStatusEnum> {

        public RequestStatusEnumFilter() {
        }

        public RequestStatusEnumFilter(RequestStatusEnumFilter filter) {
            super(filter);
        }

        @Override
        public RequestStatusEnumFilter copy() {
            return new RequestStatusEnumFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private RequestStatusEnumFilter status;

    private LocalDateFilter updatedAt;

    private LongFilter requestId;

    public RequestStatusCriteria() {
    }

    public RequestStatusCriteria(RequestStatusCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.requestId = other.requestId == null ? null : other.requestId.copy();
    }

    @Override
    public RequestStatusCriteria copy() {
        return new RequestStatusCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public RequestStatusEnumFilter getStatus() {
        return status;
    }

    public void setStatus(RequestStatusEnumFilter status) {
        this.status = status;
    }

    public LocalDateFilter getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateFilter updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LongFilter getRequestId() {
        return requestId;
    }

    public void setRequestId(LongFilter requestId) {
        this.requestId = requestId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RequestStatusCriteria that = (RequestStatusCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(status, that.status) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(requestId, that.requestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        status,
        updatedAt,
        requestId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestStatusCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
                (requestId != null ? "requestId=" + requestId + ", " : "") +
            "}";
    }

}
