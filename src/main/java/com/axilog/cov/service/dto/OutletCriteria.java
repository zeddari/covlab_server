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

    private LongFilter outletId;

    private StringFilter outletName;

    private StringFilter outletRegion;

    private StringFilter outletAdress;

    private DoubleFilter outletLat;

    private DoubleFilter outletLng;

    private LongFilter inventoryId;

    public OutletCriteria() {
    }

    public OutletCriteria(OutletCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.outletId = other.outletId == null ? null : other.outletId.copy();
        this.outletName = other.outletName == null ? null : other.outletName.copy();
        this.outletRegion = other.outletRegion == null ? null : other.outletRegion.copy();
        this.outletAdress = other.outletAdress == null ? null : other.outletAdress.copy();
        this.outletLat = other.outletLat == null ? null : other.outletLat.copy();
        this.outletLng = other.outletLng == null ? null : other.outletLng.copy();
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

    public LongFilter getOutletId() {
        return outletId;
    }

    public void setOutletId(LongFilter outletId) {
        this.outletId = outletId;
    }

    public StringFilter getOutletName() {
        return outletName;
    }

    public void setOutletName(StringFilter outletName) {
        this.outletName = outletName;
    }

    public StringFilter getOutletRegion() {
        return outletRegion;
    }

    public void setOutletRegion(StringFilter outletRegion) {
        this.outletRegion = outletRegion;
    }

    public StringFilter getOutletAdress() {
        return outletAdress;
    }

    public void setOutletAdress(StringFilter outletAdress) {
        this.outletAdress = outletAdress;
    }

    public DoubleFilter getOutletLat() {
        return outletLat;
    }

    public void setOutletLat(DoubleFilter outletLat) {
        this.outletLat = outletLat;
    }

    public DoubleFilter getOutletLng() {
        return outletLng;
    }

    public void setOutletLng(DoubleFilter outletLng) {
        this.outletLng = outletLng;
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
        return
            Objects.equals(id, that.id) &&
            Objects.equals(outletId, that.outletId) &&
            Objects.equals(outletName, that.outletName) &&
            Objects.equals(outletRegion, that.outletRegion) &&
            Objects.equals(outletAdress, that.outletAdress) &&
            Objects.equals(outletLat, that.outletLat) &&
            Objects.equals(outletLng, that.outletLng) &&
            Objects.equals(inventoryId, that.inventoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        outletId,
        outletName,
        outletRegion,
        outletAdress,
        outletLat,
        outletLng,
        inventoryId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OutletCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (outletId != null ? "outletId=" + outletId + ", " : "") +
                (outletName != null ? "outletName=" + outletName + ", " : "") +
                (outletRegion != null ? "outletRegion=" + outletRegion + ", " : "") +
                (outletAdress != null ? "outletAdress=" + outletAdress + ", " : "") +
                (outletLat != null ? "outletLat=" + outletLat + ", " : "") +
                (outletLng != null ? "outletLng=" + outletLng + ", " : "") +
                (inventoryId != null ? "inventoryId=" + inventoryId + ", " : "") +
            "}";
    }

}
