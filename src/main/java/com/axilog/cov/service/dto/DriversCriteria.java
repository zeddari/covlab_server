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
 * Criteria class for the {@link com.axilog.cov.domain.Tickets} entity. This class is used
 * in {@link com.axilog.cov.web.rest.TicketsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tickets?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DriversCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter idDrivers;


    private StringFilter nameDrivers;




    public DriversCriteria() {
    }

    public DriversCriteria(DriversCriteria other) {
        this.idDrivers = other.idDrivers == null ? null : other.idDrivers.copy();
        this.nameDrivers = other.nameDrivers == null ? null : other.nameDrivers.copy();

    }

    @Override
    public DriversCriteria copy() {
        return new DriversCriteria(this);
    }

    public LongFilter getIdDrivers() {
        return idDrivers;
    }

    public void setIdDrivers(LongFilter id) {
        this.idDrivers = id;
    }

    

    public StringFilter getNameDrivers() {
        return nameDrivers;
    }

    public void setNameDrivers(StringFilter nameDrivers) {
        this.nameDrivers = nameDrivers;
    }
    


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DriversCriteria that = (DriversCriteria) o;
        return
            Objects.equals(idDrivers, that.idDrivers) &&
            Objects.equals(nameDrivers, that.nameDrivers);
        
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        idDrivers,
        nameDrivers
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DriversCriteria{" +
                (idDrivers != null ? "idDrivers=" + idDrivers + ", " : "") +
                (nameDrivers != null ? "ticketNo=" + nameDrivers + ", " : "") +
                            "}";
    }

}
