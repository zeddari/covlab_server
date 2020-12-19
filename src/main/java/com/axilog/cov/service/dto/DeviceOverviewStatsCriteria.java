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
 * Criteria class for the {@link com.axilog.cov.domain.DeviceOverviewStats} entity. This class is used
 * in {@link com.axilog.cov.web.rest.DeviceOverviewStatsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /device-overview-stats?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DeviceOverviewStatsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter deviceId;

    private LocalDateFilter timestamp;

    private StringFilter serialNumber;

    private DoubleFilter humidity;

    private DoubleFilter temperature;

    private DoubleFilter co2;

    private DoubleFilter pressure;

    private DoubleFilter differentialPressure;

    private LongFilter productId;

    private LongFilter outletId;

    public DeviceOverviewStatsCriteria() {
    }

    public DeviceOverviewStatsCriteria(DeviceOverviewStatsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.deviceId = other.deviceId == null ? null : other.deviceId.copy();
        this.timestamp = other.timestamp == null ? null : other.timestamp.copy();
        this.serialNumber = other.serialNumber == null ? null : other.serialNumber.copy();
        this.humidity = other.humidity == null ? null : other.humidity.copy();
        this.temperature = other.temperature == null ? null : other.temperature.copy();
        this.co2 = other.co2 == null ? null : other.co2.copy();
        this.pressure = other.pressure == null ? null : other.pressure.copy();
        this.differentialPressure = other.differentialPressure == null ? null : other.differentialPressure.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.outletId = other.outletId == null ? null : other.outletId.copy();
    }

    @Override
    public DeviceOverviewStatsCriteria copy() {
        return new DeviceOverviewStatsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(LongFilter deviceId) {
        this.deviceId = deviceId;
    }

    public LocalDateFilter getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateFilter timestamp) {
        this.timestamp = timestamp;
    }

    public StringFilter getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(StringFilter serialNumber) {
        this.serialNumber = serialNumber;
    }

    public DoubleFilter getHumidity() {
        return humidity;
    }

    public void setHumidity(DoubleFilter humidity) {
        this.humidity = humidity;
    }

    public DoubleFilter getTemperature() {
        return temperature;
    }

    public void setTemperature(DoubleFilter temperature) {
        this.temperature = temperature;
    }

    public DoubleFilter getCo2() {
        return co2;
    }

    public void setCo2(DoubleFilter co2) {
        this.co2 = co2;
    }

    public DoubleFilter getPressure() {
        return pressure;
    }

    public void setPressure(DoubleFilter pressure) {
        this.pressure = pressure;
    }

    public DoubleFilter getDifferentialPressure() {
        return differentialPressure;
    }

    public void setDifferentialPressure(DoubleFilter differentialPressure) {
        this.differentialPressure = differentialPressure;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public LongFilter getOutletId() {
        return outletId;
    }

    public void setOutletId(LongFilter outletId) {
        this.outletId = outletId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DeviceOverviewStatsCriteria that = (DeviceOverviewStatsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(deviceId, that.deviceId) &&
            Objects.equals(timestamp, that.timestamp) &&
            Objects.equals(serialNumber, that.serialNumber) &&
            Objects.equals(humidity, that.humidity) &&
            Objects.equals(temperature, that.temperature) &&
            Objects.equals(co2, that.co2) &&
            Objects.equals(pressure, that.pressure) &&
            Objects.equals(differentialPressure, that.differentialPressure) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(outletId, that.outletId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        deviceId,
        timestamp,
        serialNumber,
        humidity,
        temperature,
        co2,
        pressure,
        differentialPressure,
        productId,
        outletId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeviceOverviewStatsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (deviceId != null ? "deviceId=" + deviceId + ", " : "") +
                (timestamp != null ? "timestamp=" + timestamp + ", " : "") +
                (serialNumber != null ? "serialNumber=" + serialNumber + ", " : "") +
                (humidity != null ? "humidity=" + humidity + ", " : "") +
                (temperature != null ? "temperature=" + temperature + ", " : "") +
                (co2 != null ? "co2=" + co2 + ", " : "") +
                (pressure != null ? "pressure=" + pressure + ", " : "") +
                (differentialPressure != null ? "differentialPressure=" + differentialPressure + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
                (outletId != null ? "outletId=" + outletId + ", " : "") +
            "}";
    }

}
