package com.axilog.cov.dto.criteria;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.axilog.myflow.domain.Sla} entity. This class is used
 * in {@link com.axilog.myflow.web.rest.SlaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /slas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SlaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter startDateTime;

    private LocalDateFilter endDateTime;

    private LongFilter duration;

    private LocalDateFilter dueDateTime;

    private StringFilter context;

    private BooleanFilter outOfSla;

    private StringFilter waiverRequested;

    private StringFilter waiverReason;

    private LongFilter requestId;

    public SlaCriteria() {
    }

    public SlaCriteria(SlaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.startDateTime = other.startDateTime == null ? null : other.startDateTime.copy();
        this.endDateTime = other.endDateTime == null ? null : other.endDateTime.copy();
        this.duration = other.duration == null ? null : other.duration.copy();
        this.dueDateTime = other.dueDateTime == null ? null : other.dueDateTime.copy();
        this.context = other.context == null ? null : other.context.copy();
        this.outOfSla = other.outOfSla == null ? null : other.outOfSla.copy();
        this.waiverRequested = other.waiverRequested == null ? null : other.waiverRequested.copy();
        this.waiverReason = other.waiverReason == null ? null : other.waiverReason.copy();
        this.requestId = other.requestId == null ? null : other.requestId.copy();
    }

    @Override
    public SlaCriteria copy() {
        return new SlaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateFilter startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateFilter getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateFilter endDateTime) {
        this.endDateTime = endDateTime;
    }

    public LongFilter getDuration() {
        return duration;
    }

    public void setDuration(LongFilter duration) {
        this.duration = duration;
    }

    public LocalDateFilter getDueDateTime() {
        return dueDateTime;
    }

    public void setDueDateTime(LocalDateFilter dueDateTime) {
        this.dueDateTime = dueDateTime;
    }

    public StringFilter getContext() {
        return context;
    }

    public void setContext(StringFilter context) {
        this.context = context;
    }

    public BooleanFilter getOutOfSla() {
        return outOfSla;
    }

    public void setOutOfSla(BooleanFilter outOfSla) {
        this.outOfSla = outOfSla;
    }

    public StringFilter getWaiverRequested() {
        return waiverRequested;
    }

    public void setWaiverRequested(StringFilter waiverRequested) {
        this.waiverRequested = waiverRequested;
    }

    public StringFilter getWaiverReason() {
        return waiverReason;
    }

    public void setWaiverReason(StringFilter waiverReason) {
        this.waiverReason = waiverReason;
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
        final SlaCriteria that = (SlaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(startDateTime, that.startDateTime) &&
            Objects.equals(endDateTime, that.endDateTime) &&
            Objects.equals(duration, that.duration) &&
            Objects.equals(dueDateTime, that.dueDateTime) &&
            Objects.equals(context, that.context) &&
            Objects.equals(outOfSla, that.outOfSla) &&
            Objects.equals(waiverRequested, that.waiverRequested) &&
            Objects.equals(waiverReason, that.waiverReason) &&
            Objects.equals(requestId, that.requestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        startDateTime,
        endDateTime,
        duration,
        dueDateTime,
        context,
        outOfSla,
        waiverRequested,
        waiverReason,
        requestId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SlaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (startDateTime != null ? "startDateTime=" + startDateTime + ", " : "") +
                (endDateTime != null ? "endDateTime=" + endDateTime + ", " : "") +
                (duration != null ? "duration=" + duration + ", " : "") +
                (dueDateTime != null ? "dueDateTime=" + dueDateTime + ", " : "") +
                (context != null ? "context=" + context + ", " : "") +
                (outOfSla != null ? "outOfSla=" + outOfSla + ", " : "") +
                (waiverRequested != null ? "waiverRequested=" + waiverRequested + ", " : "") +
                (waiverReason != null ? "waiverReason=" + waiverReason + ", " : "") +
                (requestId != null ? "requestId=" + requestId + ", " : "") +
            "}";
    }

}
