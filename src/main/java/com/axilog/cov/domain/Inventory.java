package com.axilog.cov.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A Inventory.
 */
@Entity
@Table(name = "inventory")
public class Inventory implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inventory_id")
    private String inventoryId;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "description")
    private String description;

    @Column(name = "quantities_in_hand")
    private String quantitiesInHand;

    @Column(name = "quantities_in_transit")
    private String quantitiesInTransit;

    @Column(name = "uom")
    private String uom;

    @Column(name = "actual_daily_consumption")
    private String actualDailyConsumption;

    @Column(name = "record_level")
    private String recordLevel;

    @Column(name = "suggested_quantity")
    private String suggestedQuantity;

    @Column(name = "expected_covering_day")
    private String expectedCoveringDay;

    @Column(name = "quantity")
    private String quantity;

    @Column(name = "location")
    private String location;

    @Column(name = "laster_updated")
    private LocalDate lasterUpdated;

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

    public String getInventoryId() {
        return inventoryId;
    }

    public Inventory inventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
        return this;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public Inventory itemCode(String itemCode) {
        this.itemCode = itemCode;
        return this;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public Inventory description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuantitiesInHand() {
        return quantitiesInHand;
    }

    public Inventory quantitiesInHand(String quantitiesInHand) {
        this.quantitiesInHand = quantitiesInHand;
        return this;
    }

    public void setQuantitiesInHand(String quantitiesInHand) {
        this.quantitiesInHand = quantitiesInHand;
    }

    public String getQuantitiesInTransit() {
        return quantitiesInTransit;
    }

    public Inventory quantitiesInTransit(String quantitiesInTransit) {
        this.quantitiesInTransit = quantitiesInTransit;
        return this;
    }

    public void setQuantitiesInTransit(String quantitiesInTransit) {
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

    public String getActualDailyConsumption() {
        return actualDailyConsumption;
    }

    public Inventory actualDailyConsumption(String actualDailyConsumption) {
        this.actualDailyConsumption = actualDailyConsumption;
        return this;
    }

    public void setActualDailyConsumption(String actualDailyConsumption) {
        this.actualDailyConsumption = actualDailyConsumption;
    }

    public String getRecordLevel() {
        return recordLevel;
    }

    public Inventory recordLevel(String recordLevel) {
        this.recordLevel = recordLevel;
        return this;
    }

    public void setRecordLevel(String recordLevel) {
        this.recordLevel = recordLevel;
    }

    public String getSuggestedQuantity() {
        return suggestedQuantity;
    }

    public Inventory suggestedQuantity(String suggestedQuantity) {
        this.suggestedQuantity = suggestedQuantity;
        return this;
    }

    public void setSuggestedQuantity(String suggestedQuantity) {
        this.suggestedQuantity = suggestedQuantity;
    }

    public String getExpectedCoveringDay() {
        return expectedCoveringDay;
    }

    public Inventory expectedCoveringDay(String expectedCoveringDay) {
        this.expectedCoveringDay = expectedCoveringDay;
        return this;
    }

    public void setExpectedCoveringDay(String expectedCoveringDay) {
        this.expectedCoveringDay = expectedCoveringDay;
    }

    public String getQuantity() {
        return quantity;
    }

    public Inventory quantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return location;
    }

    public Inventory location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getLasterUpdated() {
        return lasterUpdated;
    }

    public Inventory lasterUpdated(LocalDate lasterUpdated) {
        this.lasterUpdated = lasterUpdated;
        return this;
    }

    public void setLasterUpdated(LocalDate lasterUpdated) {
        this.lasterUpdated = lasterUpdated;
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
            ", inventoryId='" + getInventoryId() + "'" +
            ", itemCode='" + getItemCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", quantitiesInHand='" + getQuantitiesInHand() + "'" +
            ", quantitiesInTransit='" + getQuantitiesInTransit() + "'" +
            ", uom='" + getUom() + "'" +
            ", actualDailyConsumption='" + getActualDailyConsumption() + "'" +
            ", recordLevel='" + getRecordLevel() + "'" +
            ", suggestedQuantity='" + getSuggestedQuantity() + "'" +
            ", expectedCoveringDay='" + getExpectedCoveringDay() + "'" +
            ", quantity='" + getQuantity() + "'" +
            ", location='" + getLocation() + "'" +
            ", lasterUpdated='" + getLasterUpdated() + "'" +
            "}";
    }
}
