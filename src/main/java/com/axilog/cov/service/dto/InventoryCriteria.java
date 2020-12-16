package com.axilog.cov.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import java.io.Serializable;
import java.util.Objects;

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

    private StringFilter inventoryId;

    private StringFilter itemCode;

    private StringFilter description;

    private StringFilter quantitiesInHand;

    private StringFilter quantitiesInTransit;

    private StringFilter uom;

    private StringFilter actualDailyConsumption;

    private StringFilter recordLevel;

    private StringFilter suggestedQuantity;

    private StringFilter expectedCoveringDay;

    private StringFilter quantity;

    private StringFilter location;

    private LocalDateFilter lasterUpdated;

    private LongFilter outletId;

    private LongFilter productId;

    public InventoryCriteria() {}

    public InventoryCriteria(InventoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.inventoryId = other.inventoryId == null ? null : other.inventoryId.copy();
        this.itemCode = other.itemCode == null ? null : other.itemCode.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.quantitiesInHand = other.quantitiesInHand == null ? null : other.quantitiesInHand.copy();
        this.quantitiesInTransit = other.quantitiesInTransit == null ? null : other.quantitiesInTransit.copy();
        this.uom = other.uom == null ? null : other.uom.copy();
        this.actualDailyConsumption = other.actualDailyConsumption == null ? null : other.actualDailyConsumption.copy();
        this.recordLevel = other.recordLevel == null ? null : other.recordLevel.copy();
        this.suggestedQuantity = other.suggestedQuantity == null ? null : other.suggestedQuantity.copy();
        this.expectedCoveringDay = other.expectedCoveringDay == null ? null : other.expectedCoveringDay.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.location = other.location == null ? null : other.location.copy();
        this.lasterUpdated = other.lasterUpdated == null ? null : other.lasterUpdated.copy();
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

    public StringFilter getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(StringFilter inventoryId) {
        this.inventoryId = inventoryId;
    }

    public StringFilter getItemCode() {
        return itemCode;
    }

    public void setItemCode(StringFilter itemCode) {
        this.itemCode = itemCode;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getQuantitiesInHand() {
        return quantitiesInHand;
    }

    public void setQuantitiesInHand(StringFilter quantitiesInHand) {
        this.quantitiesInHand = quantitiesInHand;
    }

    public StringFilter getQuantitiesInTransit() {
        return quantitiesInTransit;
    }

    public void setQuantitiesInTransit(StringFilter quantitiesInTransit) {
        this.quantitiesInTransit = quantitiesInTransit;
    }

    public StringFilter getUom() {
        return uom;
    }

    public void setUom(StringFilter uom) {
        this.uom = uom;
    }

    public StringFilter getActualDailyConsumption() {
        return actualDailyConsumption;
    }

    public void setActualDailyConsumption(StringFilter actualDailyConsumption) {
        this.actualDailyConsumption = actualDailyConsumption;
    }

    public StringFilter getRecordLevel() {
        return recordLevel;
    }

    public void setRecordLevel(StringFilter recordLevel) {
        this.recordLevel = recordLevel;
    }

    public StringFilter getSuggestedQuantity() {
        return suggestedQuantity;
    }

    public void setSuggestedQuantity(StringFilter suggestedQuantity) {
        this.suggestedQuantity = suggestedQuantity;
    }

    public StringFilter getExpectedCoveringDay() {
        return expectedCoveringDay;
    }

    public void setExpectedCoveringDay(StringFilter expectedCoveringDay) {
        this.expectedCoveringDay = expectedCoveringDay;
    }

    public StringFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(StringFilter quantity) {
        this.quantity = quantity;
    }

    public StringFilter getLocation() {
        return location;
    }

    public void setLocation(StringFilter location) {
        this.location = location;
    }

    public LocalDateFilter getLasterUpdated() {
        return lasterUpdated;
    }

    public void setLasterUpdated(LocalDateFilter lasterUpdated) {
        this.lasterUpdated = lasterUpdated;
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
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(inventoryId, that.inventoryId) &&
            Objects.equals(itemCode, that.itemCode) &&
            Objects.equals(description, that.description) &&
            Objects.equals(quantitiesInHand, that.quantitiesInHand) &&
            Objects.equals(quantitiesInTransit, that.quantitiesInTransit) &&
            Objects.equals(uom, that.uom) &&
            Objects.equals(actualDailyConsumption, that.actualDailyConsumption) &&
            Objects.equals(recordLevel, that.recordLevel) &&
            Objects.equals(suggestedQuantity, that.suggestedQuantity) &&
            Objects.equals(expectedCoveringDay, that.expectedCoveringDay) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(location, that.location) &&
            Objects.equals(lasterUpdated, that.lasterUpdated) &&
            Objects.equals(outletId, that.outletId) &&
            Objects.equals(productId, that.productId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            inventoryId,
            itemCode,
            description,
            quantitiesInHand,
            quantitiesInTransit,
            uom,
            actualDailyConsumption,
            recordLevel,
            suggestedQuantity,
            expectedCoveringDay,
            quantity,
            location,
            lasterUpdated,
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
                (itemCode != null ? "itemCode=" + itemCode + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (quantitiesInHand != null ? "quantitiesInHand=" + quantitiesInHand + ", " : "") +
                (quantitiesInTransit != null ? "quantitiesInTransit=" + quantitiesInTransit + ", " : "") +
                (uom != null ? "uom=" + uom + ", " : "") +
                (actualDailyConsumption != null ? "actualDailyConsumption=" + actualDailyConsumption + ", " : "") +
                (recordLevel != null ? "recordLevel=" + recordLevel + ", " : "") +
                (suggestedQuantity != null ? "suggestedQuantity=" + suggestedQuantity + ", " : "") +
                (expectedCoveringDay != null ? "expectedCoveringDay=" + expectedCoveringDay + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (location != null ? "location=" + location + ", " : "") +
                (lasterUpdated != null ? "lasterUpdated=" + lasterUpdated + ", " : "") +
                (outletId != null ? "outletId=" + outletId + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
            "}";
    }
}
