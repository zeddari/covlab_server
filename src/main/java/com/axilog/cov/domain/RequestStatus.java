package com.axilog.cov.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.axilog.cov.enums.RequestStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * A RequestStatus.
 */
@Entity
@Table(name = "request_status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "requeststatus")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RequestStatusEnum status;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @ManyToOne
    @JsonIgnoreProperties(value = "requestStatuses", allowSetters = true)
    private Request request;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RequestStatusEnum getStatus() {
        return status;
    }

    public RequestStatus status(RequestStatusEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(RequestStatusEnum status) {
        this.status = status;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public RequestStatus updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Request getRequest() {
        return request;
    }

    public RequestStatus request(Request request) {
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
        if (!(o instanceof RequestStatus)) {
            return false;
        }
        return id != null && id.equals(((RequestStatus) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestStatus{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
