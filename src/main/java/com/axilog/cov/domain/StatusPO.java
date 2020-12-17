package com.axilog.cov.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A StatusPO.
 */
@Entity
@Table(name = "status_po")
public class StatusPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status_po_id")
    private Long statusPoId;

    @Column(name = "status_po_name")
    private String statusPoName;

    @ManyToOne
    @JsonIgnoreProperties(value = "statusPOS", allowSetters = true)
    private PurchaseOrder purchaseOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStatusPoId() {
        return statusPoId;
    }

    public StatusPO statusPoId(Long statusPoId) {
        this.statusPoId = statusPoId;
        return this;
    }

    public void setStatusPoId(Long statusPoId) {
        this.statusPoId = statusPoId;
    }

    public String getStatusPoName() {
        return statusPoName;
    }

    public StatusPO statusPoName(String statusPoName) {
        this.statusPoName = statusPoName;
        return this;
    }

    public void setStatusPoName(String statusPoName) {
        this.statusPoName = statusPoName;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public StatusPO purchaseOrder(PurchaseOrder purchaseOrder) {
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
        if (!(o instanceof StatusPO)) {
            return false;
        }
        return id != null && id.equals(((StatusPO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatusPO{" +
            "id=" + getId() +
            ", statusPoId=" + getStatusPoId() +
            ", statusPoName='" + getStatusPoName() + "'" +
            "}";
    }
}
