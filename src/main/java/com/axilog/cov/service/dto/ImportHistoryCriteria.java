package com.axilog.cov.service.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.IntegerFilter;
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
public class ImportHistoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

  
    private IntegerFilter id;
    private StringFilter fileName;
    private StringFilter jobId;
   private StringFilter outlet;
    private LocalDateFilter importedAt;
    private StringFilter importedBy;
    private StringFilter status;
    private StringFilter message;
    private StringFilter nupcoCode;
    private StringFilter result;
    
    

    public ImportHistoryCriteria() {
    }

    public ImportHistoryCriteria(ImportHistoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fileName = other.fileName == null ? null : other.fileName.copy();
        this.jobId = other.jobId == null ? null : other.jobId.copy();
        this.outlet = other.outlet == null ? null : other.outlet.copy();
        this.importedAt = other.importedAt == null ? null : other.importedAt.copy();
        this.importedBy = other.importedBy == null ? null : other.importedBy.copy();
        this.message = other.message == null ? null : other.message.copy();
        this.nupcoCode = other.nupcoCode == null ? null : other.nupcoCode.copy();
        this.result = other.result == null ? null : other.result.copy();
        this.status = other.status == null ? null : other.status.copy();
    }

    @Override
    public ImportHistoryCriteria copy() {
        return new ImportHistoryCriteria(this);
    }

    public IntegerFilter getId() {
        return id;
    }

    public void setId(IntegerFilter id) {
        this.id = id;
    }



    public StringFilter getfileName() {
        return fileName;
    }

    public void setfileName(StringFilter fileName) {
        this.fileName = fileName;
    }

    public StringFilter getjobId() {
        return jobId;
    }

    public void setjobId(StringFilter jobId) {
        this.jobId = jobId;
    }

    public StringFilter getoutlet() {
        return outlet;
    }

    public void setoutlet(StringFilter outlet) {
        this.outlet = outlet;
    }

    public LocalDateFilter getimportedAt() {
        return importedAt;
    }

    public void setimportedAt(LocalDateFilter importedAt) {
        this.importedAt = importedAt;
    }

    public StringFilter getimportedBy() {
        return importedBy;
    }

    public void setimportedBy(StringFilter importedBy) {
        this.importedBy = importedBy;
    }

    public StringFilter getmessage() {
        return message;
    }

    public void setmessage(StringFilter message) {
        this.message = message;
    }

    public StringFilter getnupcoCode() {
        return nupcoCode;
    }

    public void setnupcoCode(StringFilter nupcoCode) {
        this.nupcoCode = nupcoCode;
    }

    public StringFilter getresult() {
        return result;
    }

    public void setresult(StringFilter result) {
        this.result = result;
    }



    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

 

  


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ImportHistoryCriteria that = (ImportHistoryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fileName, that.fileName) &&
            Objects.equals(jobId, that.jobId) &&
            Objects.equals(outlet, that.outlet) &&
            Objects.equals(importedAt, that.importedAt) &&
            Objects.equals(importedBy, that.importedBy) &&
            Objects.equals(message, that.message) &&
            Objects.equals(nupcoCode, that.nupcoCode) &&
            Objects.equals(result, that.result) &&
            Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fileName,
        jobId,
        outlet,
        importedAt,
        importedBy,
        message,
        nupcoCode,
        result,
        status
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImportHistoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fileName != null ? "fileName=" + fileName + ", " : "") +
                (jobId != null ? "jobId=" + jobId + ", " : "") +
                (outlet != null ? "outlet=" + outlet + ", " : "") +
                (importedAt != null ? "importedAt=" + importedAt + ", " : "") +
                (importedBy != null ? "importedBy=" + importedBy + ", " : "") +
                (message != null ? "message=" + message + ", " : "") +
                (nupcoCode != null ? "nupcoCode=" + nupcoCode + ", " : "") +
                (result != null ? "result=" + result + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
            "}";
    }

}
