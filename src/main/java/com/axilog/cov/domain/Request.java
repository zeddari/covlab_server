package com.axilog.cov.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A Request.
 */
@Entity
@Table(name = "request")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "request")
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "request_id")
    private String requestId;

    @OneToMany(mappedBy = "request")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<RequestStatus> requestStatuses = new HashSet<>();

    @OneToMany(mappedBy = "request")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Sla> slas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "requests", allowSetters = true)
    private Quotation quotation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public Request createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Request createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getName() {
        return name;
    }

    public Request name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Request description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequestId() {
        return requestId;
    }

    public Request requestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Set<RequestStatus> getRequestStatuses() {
    	if (requestStatuses == null) return new HashSet<>();
        return requestStatuses;
    }

    public Request requestStatuses(Set<RequestStatus> requestStatuses) {
        this.requestStatuses = requestStatuses;
        return this;
    }

    public Request addRequestStatus(RequestStatus requestStatus) {
        this.requestStatuses.add(requestStatus);
        requestStatus.setRequest(this);
        return this;
    }

    public Request removeRequestStatus(RequestStatus requestStatus) {
        this.requestStatuses.remove(requestStatus);
        requestStatus.setRequest(null);
        return this;
    }

    public void setRequestStatuses(Set<RequestStatus> requestStatuses) {
        this.requestStatuses = requestStatuses;
    }

    public Set<Sla> getSlas() {
        return slas;
    }

    public Request slas(Set<Sla> slas) {
        this.slas = slas;
        return this;
    }

    public Request addSla(Sla sla) {
        this.slas.add(sla);
        sla.setRequest(this);
        return this;
    }

    public Request removeSla(Sla sla) {
        this.slas.remove(sla);
        sla.setRequest(null);
        return this;
    }

    public void setSlas(Set<Sla> slas) {
        this.slas = slas;
    }

    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Request)) {
            return false;
        }
        return id != null && id.equals(((Request) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Request{" +
            "id=" + getId() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", requestId='" + getRequestId() + "'" +
            "}";
    }
}
