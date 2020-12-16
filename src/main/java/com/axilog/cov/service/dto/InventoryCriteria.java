package com.axilog.cov.service.dto;

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
 * Criteria class for the {@link com.axilog.cov.domain.Inventory} entity. This class is used
 * in {@link com.axilog.cov.web.rest.InventoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /inventories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InventoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter inventoryId;

    private DoubleFilter quantitiesInHand;

    private DoubleFilter quantitiesInTransit;

    private StringFilter uom;

    private DoubleFilter actualDailyConsumption;

    private DoubleFilter actualAvgConsumption;

    private StringFilter reOrderLevel;

    private DoubleFilter suggestedQuantity;

    private DoubleFilter expectedCoveringDay;

    private LocalDateFilter lastUpdatedAt;

    private StringFilter status;

    private LongFilter outletId;

    private LongFilter productId;

    public InventoryCriteria() {
    }

    public InventoryCriteria(InventoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.inventoryId = other.inventoryId == null ? null : other.inventoryId.copy();
        this.quantitiesInHand = other.quantitiesInHand == null ? null : other.quantitiesInHand.copy();
        this.quantitiesInTransit = other.quantitiesInTransit == null ? null : other.quantitiesInTransit.copy();
        this.uom = other.uom == null ? null : other.uom.copy();
        this.actualDailyConsumption = other.actualDailyConsumption == null ? null : other.actualDailyConsumption.copy();
        this.actualAvgConsumption = other.actualAvgConsumption == null ? null : other.actualAvgConsumption.copy();
        this.reOrderLevel = other.reOrderLevel == null ? null : other.reOrderLevel.copy();
        this.suggestedQuantity = other.suggestedQuantity == null ? null : other.suggestedQuantity.copy();
        this.expectedCoveringDay = other.expectedCoveringDay == null ? null : other.expectedCoveringDay.copy();
        this.lastUpdatedAt = other.lastUpdatedAt == null ? null : other.lastUpdatedAt.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.outletId = other.outletId == null ? null : other.outletId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
    }

    @Override
    public InventoryCriteria copy() {
        return new InventoryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(LongFilter inventoryId) {
        this.inventoryId = inventoryId;
    }

    public DoubleFilter getQuantitiesInHand() {
        return quantitiesInHand;
    }

    public void setQuantitiesInHand(DoubleFilter quantitiesInHand) {
        this.quantitiesInHand = quantitiesInHand;
    }

    public DoubleFilter getQuantitiesInTransit() {
        return quantitiesInTransit;
    }

    public void setQuantitiesInTransit(DoubleFilter quantitiesInTransit) {
        this.quantitiesInTransit = quantitiesInTransit;
    }

    public StringFilter getUom() {
        return uom;
    }

    public void setUom(StringFilter uom) {
        this.uom = uom;
    }

    public DoubleFilter getActualDailyConsumption() {
        return actualDailyConsumption;
    }

    public void setActualDailyConsumption(DoubleFilter actualDailyConsumption) {
        this.actualDailyConsumption = actualDailyConsumption;
    }

    public DoubleFilter getActualAvgConsumption() {
        return actualAvgConsumption;
    }

    public void setActualAvgConsumption(DoubleFilter actualAvgConsumption) {
        this.actualAvgConsumption = actualAvgConsumption;
    }

    public StringFilter getReOrderLevel() {
        return reOrderLevel;
    }

    public void setReOrderLevel(StringFilter reOrderLevel) {
        this.reOrderLevel = reOrderLevel;
    }

    public DoubleFilter getSuggestedQuantity() {
        return suggestedQuantity;
    }

    public void setSuggestedQuantity(DoubleFilter suggestedQuantity) {
        this.suggestedQuantity = suggestedQuantity;
    }

    public DoubleFilter getExpectedCoveringDay() {
        return expectedCoveringDay;
    }

    public void setExpectedCoveringDay(DoubleFilter expectedCoveringDay) {
        this.expectedCoveringDay = expectedCoveringDay;
    }

    public LocalDateFilter getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(LocalDateFilter lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public LongFilter getOutletId() {
        return outletId;
    }

    public void setOutletId(LongFilter outletId) {
        this.outletId = outletId;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InventoryCriteria that = (InventoryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(inventoryId, that.inventoryId) &&
            Objects.equals(quantitiesInHand, that.quantitiesInHand) &&
            Objects.equals(quantitiesInTransit, that.quantitiesInTransit) &&
            Objects.equals(uom, that.uom) &&
            Objects.equals(actualDailyConsumption, that.actualDailyConsumption) &&
            Objects.equals(actualAvgConsumption, that.actualAvgConsumption) &&
            Objects.equals(reOrderLevel, that.reOrderLevel) &&
            Objects.equals(suggestedQuantity, that.suggestedQuantity) &&
            Objects.equals(expectedCoveringDay, that.expectedCoveringDay) &&
            Objects.equals(lastUpdatedAt, that.lastUpdatedAt) &&
            Objects.equals(status, that.status) &&
            Objects.equals(outletId, that.outletId) &&
            Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        inventoryId,
        quantitiesInHand,
        quantitiesInTransit,
        uom,
        actualDailyConsumption,
        actualAvgConsumption,
        reOrderLevel,
        suggestedQuantity,
        expectedCoveringDay,
        lastUpdatedAt,
        status,
        outletId,
        productId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (inventoryId != null ? "inventoryId=" + inventoryId + ", " : "") +
                (quantitiesInHand != null ? "quantitiesInHand=" + quantitiesInHand + ", " : "") +
                (quantitiesInTransit != null ? "quantitiesInTransit=" + quantitiesInTransit + ", " : "") +
                (uom != null ? "uom=" + uom + ", " : "") +
                (actualDailyConsumption != null ? "actualDailyConsumption=" + actualDailyConsumption + ", " : "") +
                (actualAvgConsumption != null ? "actualAvgConsumption=" + actualAvgConsumption + ", " : "") +
                (reOrderLevel != null ? "reOrderLevel=" + reOrderLevel + ", " : "") +
                (suggestedQuantity != null ? "suggestedQuantity=" + suggestedQuantity + ", " : "") +
                (expectedCoveringDay != null ? "expectedCoveringDay=" + expectedCoveringDay + ", " : "") +
                (lastUpdatedAt != null ? "lastUpdatedAt=" + lastUpdatedAt + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (outletId != null ? "outletId=" + outletId + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
            "}";
    }

}
