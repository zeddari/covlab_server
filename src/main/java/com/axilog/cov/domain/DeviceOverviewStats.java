package com.axilog.cov.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A DeviceOverviewStats.
 */
@Entity
@Table(name = "device_overview_stats")
public class DeviceOverviewStats implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "timestamp")
    private LocalDate timestamp;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "humidity")
    private Double humidity;

    @Column(name = "temperature")
    private Double temperature;

    @Column(name = "co_2")
    private Double co2;

    @Column(name = "pressure")
    private Double pressure;

    @Column(name = "differential_pressure")
    private Double differentialPressure;

    @OneToMany(mappedBy = "deviceOverviewStats")
    private Set<Product> products = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "deviceOverviewStats", allowSetters = true)
    private Outlet outlet;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public DeviceOverviewStats deviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public DeviceOverviewStats timestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public DeviceOverviewStats serialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Double getHumidity() {
        return humidity;
    }

    public DeviceOverviewStats humidity(Double humidity) {
        this.humidity = humidity;
        return this;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getTemperature() {
        return temperature;
    }

    public DeviceOverviewStats temperature(Double temperature) {
        this.temperature = temperature;
        return this;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getCo2() {
        return co2;
    }

    public DeviceOverviewStats co2(Double co2) {
        this.co2 = co2;
        return this;
    }

    public void setCo2(Double co2) {
        this.co2 = co2;
    }

    public Double getPressure() {
        return pressure;
    }

    public DeviceOverviewStats pressure(Double pressure) {
        this.pressure = pressure;
        return this;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getDifferentialPressure() {
        return differentialPressure;
    }

    public DeviceOverviewStats differentialPressure(Double differentialPressure) {
        this.differentialPressure = differentialPressure;
        return this;
    }

    public void setDifferentialPressure(Double differentialPressure) {
        this.differentialPressure = differentialPressure;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public DeviceOverviewStats products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public DeviceOverviewStats addProduct(Product product) {
        this.products.add(product);
        product.setDeviceOverviewStats(this);
        return this;
    }

    public DeviceOverviewStats removeProduct(Product product) {
        this.products.remove(product);
        product.setDeviceOverviewStats(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Outlet getOutlet() {
        return outlet;
    }

    public DeviceOverviewStats outlet(Outlet outlet) {
        this.outlet = outlet;
        return this;
    }

    public void setOutlet(Outlet outlet) {
        this.outlet = outlet;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceOverviewStats)) {
            return false;
        }
        return id != null && id.equals(((DeviceOverviewStats) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeviceOverviewStats{" +
            "id=" + getId() +
            ", deviceId='" + getDeviceId() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", humidity=" + getHumidity() +
            ", temperature=" + getTemperature() +
            ", co2=" + getCo2() +
            ", pressure=" + getPressure() +
            ", differentialPressure=" + getDifferentialPressure() +
            "}";
    }
}
