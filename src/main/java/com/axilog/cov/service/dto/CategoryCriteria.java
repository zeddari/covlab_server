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
 * Criteria class for the {@link com.axilog.cov.domain.Category} entity. This class is used
 * in {@link com.axilog.cov.web.rest.CategoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CategoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter categoryId;

    private StringFilter categoryCode;

    private StringFilter categoryDescription;

    private LongFilter productsId;

    public CategoryCriteria() {
    }

    public CategoryCriteria(CategoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
        this.categoryCode = other.categoryCode == null ? null : other.categoryCode.copy();
        this.categoryDescription = other.categoryDescription == null ? null : other.categoryDescription.copy();
        this.productsId = other.productsId == null ? null : other.productsId.copy();
    }

    @Override
    public CategoryCriteria copy() {
        return new CategoryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }

    public StringFilter getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(StringFilter categoryCode) {
        this.categoryCode = categoryCode;
    }

    public StringFilter getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(StringFilter categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public LongFilter getProductsId() {
        return productsId;
    }

    public void setProductsId(LongFilter productsId) {
        this.productsId = productsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CategoryCriteria that = (CategoryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(categoryId, that.categoryId) &&
            Objects.equals(categoryCode, that.categoryCode) &&
            Objects.equals(categoryDescription, that.categoryDescription) &&
            Objects.equals(productsId, that.productsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        categoryId,
        categoryCode,
        categoryDescription,
        productsId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
                (categoryCode != null ? "categoryCode=" + categoryCode + ", " : "") +
                (categoryDescription != null ? "categoryDescription=" + categoryDescription + ", " : "") +
                (productsId != null ? "productsId=" + productsId + ", " : "") +
            "}";
    }

}
