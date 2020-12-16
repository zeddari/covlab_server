package com.axilog.cov.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.axilog.cov.domain.Outlet} entity. This class is used
 * in {@link com.axilog.cov.web.rest.OutletResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /outlets?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OutletCriteria implements Serializable, Criteria {
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter outletId;

    private StringFilter outletName;

    private StringFilter outletLocation;

    private StringFilter outletLat;

    private StringFilter outletLong;

    private LongFilter inventoryId;

    public OutletCriteria() {}

    public OutletCriteria(OutletCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.outletId = other.outletId == null ? null : other.outletId.copy();
        this.outletName = other.outletName == null ? null : other.outletName.copy();
        this.outletLocation = other.outletLocation == null ? null : other.outletLocation.copy();
        this.outletLat = other.outletLat == null ? null : other.outletLat.copy();
        this.outletLong = other.outletLong == null ? null : other.outletLong.copy();
        this.inventoryId = other.inventoryId == null ? null : other.inventoryId.copy();
    }

    @Override
    public OutletCriteria copy() {
        return new OutletCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getOutletId() {
        return outletId;
    }

    public void setOutletId(StringFilter outletId) {
        this.outletId = outletId;
    }

    public StringFilter getOutletName() {
        return outletName;
    }

    public void setOutletName(StringFilter outletName) {
        this.outletName = outletName;
    }

    public StringFilter getOutletLocation() {
        return outletLocation;
    }

    public void setOutletLocation(StringFilter outletLocation) {
        this.outletLocation = outletLocation;
    }

    public StringFilter getOutletLat() {
        return outletLat;
    }

    public void setOutletLat(StringFilter outletLat) {
        this.outletLat = outletLat;
    }

    public StringFilter getOutletLong() {
        return outletLong;
    }

    public void setOutletLong(StringFilter outletLong) {
        this.outletLong = outletLong;
    }

    public LongFilter getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(LongFilter inventoryId) {
        this.inventoryId = inventoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OutletCriteria that = (OutletCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(outletId, that.outletId) &&
            Objects.equals(outletName, that.outletName) &&
            Objects.equals(outletLocation, that.outletLocation) &&
            Objects.equals(outletLat, that.outletLat) &&
            Objects.equals(outletLong, that.outletLong) &&
            Objects.equals(inventoryId, that.inventoryId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, outletId, outletName, outletLocation, outletLat, outletLong, inventoryId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OutletCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (outletId != null ? "outletId=" + outletId + ", " : "") +
                (outletName != null ? "outletName=" + outletName + ", " : "") +
                (outletLocation != null ? "outletLocation=" + outletLocation + ", " : "") +
                (outletLat != null ? "outletLat=" + outletLat + ", " : "") +
                (outletLong != null ? "outletLong=" + outletLong + ", " : "") +
                (inventoryId != null ? "inventoryId=" + inventoryId + ", " : "") +
            "}";
    }
}
