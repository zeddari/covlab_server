package com.axilog.cov.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Data;

/**
 * A PoStatus.
 */
@Entity
@Table(name = "po_status")
@Builder
@Data
public class PoStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private String status;

    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne
    @JsonIgnoreProperties(value = "poStatuses", allowSetters = true)
    private PurchaseOrder purchaseOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public PoStatus status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public PoStatus purchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
        return this;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PoStatus)) {
            return false;
        }
        return id != null && id.equals(((PoStatus) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PoStatus{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
