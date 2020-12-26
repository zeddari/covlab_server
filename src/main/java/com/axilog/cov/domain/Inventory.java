package com.axilog.cov.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Inventory.
 */
@Entity
@Table(name = "inventory")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inventory implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inventory_id")
    private Long inventoryId;

    @Column(name = "quantities_in_hand")
    private Double quantitiesInHand;

    @Column(name = "quantities_in_transit")
    private Double quantitiesInTransit;

    @Column(name = "uom")
    private String uom;

    @Column(name = "actual_daily_consumption")
    private Double actualDailyConsumption;

    @Column(name = "actual_avg_consumption")
    private Double actualAvgConsumption;

    @Column(name = "re_order_level")
    private String reOrderLevel;

    @Column(name = "suggested_quantity")
    private Double suggestedQuantity;

    @Column(name = "expected_covering_day")
    private Double expectedCoveringDay;

    @Column(name = "last_updated_at")
    private Date lastUpdatedAt;

    @Column(name = "status")
    private String status;

    @Column(name = "is_last_instance")
    private Boolean isLastInstance;
    
    @ManyToOne
    @JsonIgnoreProperties(value = "inventories", allowSetters = true)
    private Outlet outlet;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventories", allowSetters = true)
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public Inventory inventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
        return this;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Double getQuantitiesInHand() {
        return quantitiesInHand;
    }

    public Inventory quantitiesInHand(Double quantitiesInHand) {
        this.quantitiesInHand = quantitiesInHand;
        return this;
    }

    public void setQuantitiesInHand(Double quantitiesInHand) {
        this.quantitiesInHand = quantitiesInHand;
    }

    public Double getQuantitiesInTransit() {
        return quantitiesInTransit;
    }

    public Inventory quantitiesInTransit(Double quantitiesInTransit) {
        this.quantitiesInTransit = quantitiesInTransit;
        return this;
    }

    public void setQuantitiesInTransit(Double quantitiesInTransit) {
        this.quantitiesInTransit = quantitiesInTransit;
    }

    public String getUom() {
        return uom;
    }

    public Inventory uom(String uom) {
        this.uom = uom;
        return this;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public Double getActualDailyConsumption() {
        return actualDailyConsumption;
    }

    public Inventory actualDailyConsumption(Double actualDailyConsumption) {
        this.actualDailyConsumption = actualDailyConsumption;
        return this;
    }

    public void setActualDailyConsumption(Double actualDailyConsumption) {
        this.actualDailyConsumption = actualDailyConsumption;
    }

    public Double getActualAvgConsumption() {
        return actualAvgConsumption;
    }

    public Inventory actualAvgConsumption(Double actualAvgConsumption) {
        this.actualAvgConsumption = actualAvgConsumption;
        return this;
    }

    public void setActualAvgConsumption(Double actualAvgConsumption) {
        this.actualAvgConsumption = actualAvgConsumption;
    }

    public String getReOrderLevel() {
        return reOrderLevel;
    }

    public Inventory reOrderLevel(String reOrderLevel) {
        this.reOrderLevel = reOrderLevel;
        return this;
    }

    public void setReOrderLevel(String reOrderLevel) {
        this.reOrderLevel = reOrderLevel;
    }

    public Double getSuggestedQuantity() {
        return suggestedQuantity;
    }

    public Inventory suggestedQuantity(Double suggestedQuantity) {
        this.suggestedQuantity = suggestedQuantity;
        return this;
    }

    public void setSuggestedQuantity(Double suggestedQuantity) {
        this.suggestedQuantity = suggestedQuantity;
    }

    public Double getExpectedCoveringDay() {
        return expectedCoveringDay;
    }

    public Inventory expectedCoveringDay(Double expectedCoveringDay) {
        this.expectedCoveringDay = expectedCoveringDay;
        return this;
    }

    public void setExpectedCoveringDay(Double expectedCoveringDay) {
        this.expectedCoveringDay = expectedCoveringDay;
    }

    public Date getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public Inventory lastUpdatedAt(Date lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
        return this;
    }

    public void setLastUpdatedAt(Date lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public String getStatus() {
        return status;
    }

    public Inventory status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Outlet getOutlet() {
        return outlet;
    }

    public Inventory outlet(Outlet outlet) {
        this.outlet = outlet;
        return this;
    }

    public void setOutlet(Outlet outlet) {
        this.outlet = outlet;
    }

    public Product getProduct() {
        return product;
    }

    public Inventory product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inventory)) {
            return false;
        }
        return id != null && id.equals(((Inventory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Inventory{" +
            "id=" + getId() +
            ", inventoryId=" + getInventoryId() +
            ", quantitiesInHand=" + getQuantitiesInHand() +
            ", quantitiesInTransit=" + getQuantitiesInTransit() +
            ", uom='" + getUom() + "'" +
            ", actualDailyConsumption=" + getActualDailyConsumption() +
            ", actualAvgConsumption=" + getActualAvgConsumption() +
            ", reOrderLevel='" + getReOrderLevel() + "'" +
            ", suggestedQuantity=" + getSuggestedQuantity() +
            ", expectedCoveringDay=" + getExpectedCoveringDay() +
            ", lastUpdatedAt='" + getLastUpdatedAt() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
