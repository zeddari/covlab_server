package com.axilog.cov.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * A Sla.
 */
@Entity
@Table(name = "sla")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sla implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date_time")
    private Date startDateTime;

    @Column(name = "end_date_time")
    private Date endDateTime;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "due_date_time")
    private Date dueDateTime;

    @Column(name = "context")
    private String context;

    @Column(name = "out_of_sla")
    private Boolean outOfSla;

    @Column(name = "waiver_requested")
    private String waiverRequested;

    @Column(name = "waiver_reason")
    private String waiverReason;

    @ManyToOne
    @JsonIgnoreProperties(value = "slas", allowSetters = true)
    private Request request;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public Sla startDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
        return this;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public Sla endDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
        return this;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Long getDuration() {
        return duration;
    }

    public Sla duration(Long duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Date getDueDateTime() {
        return dueDateTime;
    }

    public Sla dueDateTime(Date dueDateTime) {
        this.dueDateTime = dueDateTime;
        return this;
    }

    public void setDueDateTime(Date dueDateTime) {
        this.dueDateTime = dueDateTime;
    }

    public String getContext() {
        return context;
    }

    public Sla context(String context) {
        this.context = context;
        return this;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Boolean isOutOfSla() {
        return outOfSla;
    }

    public Sla outOfSla(Boolean outOfSla) {
        this.outOfSla = outOfSla;
        return this;
    }

    public void setOutOfSla(Boolean outOfSla) {
        this.outOfSla = outOfSla;
    }

    public String getWaiverRequested() {
        return waiverRequested;
    }

    public Sla waiverRequested(String waiverRequested) {
        this.waiverRequested = waiverRequested;
        return this;
    }

    public void setWaiverRequested(String waiverRequested) {
        this.waiverRequested = waiverRequested;
    }

    public String getWaiverReason() {
        return waiverReason;
    }

    public Sla waiverReason(String waiverReason) {
        this.waiverReason = waiverReason;
        return this;
    }

    public void setWaiverReason(String waiverReason) {
        this.waiverReason = waiverReason;
    }

    public Request getRequest() {
        return request;
    }

    public Sla request(Request request) {
        this.request = request;
        return this;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sla)) {
            return false;
        }
        return id != null && id.equals(((Sla) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sla{" +
            "id=" + getId() +
            ", startDateTime='" + getStartDateTime() + "'" +
            ", endDateTime='" + getEndDateTime() + "'" +
            ", duration=" + getDuration() +
            ", dueDateTime='" + getDueDateTime() + "'" +
            ", context='" + getContext() + "'" +
            ", outOfSla='" + isOutOfSla() + "'" +
            ", waiverRequested='" + getWaiverRequested() + "'" +
            ", waiverReason='" + getWaiverReason() + "'" +
            "}";
    }
}
