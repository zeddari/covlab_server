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
 * Criteria class for the {@link com.axilog.cov.domain.Product} entity. This class is used
 * in {@link com.axilog.cov.web.rest.ProductResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /products?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter productId;

    private StringFilter description;

    private StringFilter productCode;

    private StringFilter temperature;

    private LongFilter inventoriesId;

    private LongFilter purchaseOrdersId;

    private LongFilter ticketsId;

    private LongFilter categoryId;

    public ProductCriteria() {
    }

    public ProductCriteria(ProductCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.productCode = other.productCode == null ? null : other.productCode.copy();
        this.temperature = other.temperature == null ? null : other.temperature.copy();
        this.inventoriesId = other.inventoriesId == null ? null : other.inventoriesId.copy();
        this.purchaseOrdersId = other.purchaseOrdersId == null ? null : other.purchaseOrdersId.copy();
        this.ticketsId = other.ticketsId == null ? null : other.ticketsId.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
    }

    @Override
    public ProductCriteria copy() {
        return new ProductCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getProductCode() {
        return productCode;
    }

    public void setProductCode(StringFilter productCode) {
        this.productCode = productCode;
    }

    public StringFilter getTemperature() {
        return temperature;
    }

    public void setTemperature(StringFilter temperature) {
        this.temperature = temperature;
    }

    public LongFilter getInventoriesId() {
        return inventoriesId;
    }

    public void setInventoriesId(LongFilter inventoriesId) {
        this.inventoriesId = inventoriesId;
    }

    public LongFilter getPurchaseOrdersId() {
        return purchaseOrdersId;
    }

    public void setPurchaseOrdersId(LongFilter purchaseOrdersId) {
        this.purchaseOrdersId = purchaseOrdersId;
    }

    public LongFilter getTicketsId() {
        return ticketsId;
    }

    public void setTicketsId(LongFilter ticketsId) {
        this.ticketsId = ticketsId;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductCriteria that = (ProductCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(description, that.description) &&
            Objects.equals(productCode, that.productCode) &&
            Objects.equals(temperature, that.temperature) &&
            Objects.equals(inventoriesId, that.inventoriesId) &&
            Objects.equals(purchaseOrdersId, that.purchaseOrdersId) &&
            Objects.equals(ticketsId, that.ticketsId) &&
            Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        productId,
        description,
        productCode,
        temperature,
        inventoriesId,
        purchaseOrdersId,
        ticketsId,
        categoryId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (productCode != null ? "productCode=" + productCode + ", " : "") +
                (temperature != null ? "temperature=" + temperature + ", " : "") +
                (inventoriesId != null ? "inventoriesId=" + inventoriesId + ", " : "") +
                (purchaseOrdersId != null ? "purchaseOrdersId=" + purchaseOrdersId + ", " : "") +
                (ticketsId != null ? "ticketsId=" + ticketsId + ", " : "") +
                (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
            "}";
    }

}
