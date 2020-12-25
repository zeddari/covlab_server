package com.axilog.cov.domain;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Outlet.
 */
@Entity
@Table(name = "outlet")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Outlet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "outlet_id")
    private Long outletId;

    @Column(name = "outlet_name")
    private String outletName;

    @Column(name = "outlet_region")
    private String outletRegion;

    @Column(name = "outlet_adress")
    private String outletAdress;

    @Column(name = "outlet_lat")
    private Double outletLat;

    @Column(name = "outlet_lng")
    private Double outletLng;
    
    @Column(name = "outlet_type")
    private String outletType;

    @Column(name = "outlet_parent_region")
    private String outletParentRegion;
    
    @OneToMany(mappedBy = "outlet")
    private Set<Inventory> inventories = new HashSet<>();

    @OneToMany(mappedBy = "outlet")
    private Set<PurchaseOrder> purchaseOrders = new HashSet<>();

    @OneToMany(mappedBy = "outlet")
    private Set<Tickets> tickets = new HashSet<>();

    @OneToMany(mappedBy = "outlet")
    private Set<DeviceOverviewStats> deviceOverviewStats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOutletId() {
        return outletId;
    }

    public Outlet outletId(Long outletId) {
        this.outletId = outletId;
        return this;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    public String getOutletName() {
        return outletName;
    }

    public Outlet outletName(String outletName) {
        this.outletName = outletName;
        return this;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getOutletRegion() {
        return outletRegion;
    }

    public Outlet outletRegion(String outletRegion) {
        this.outletRegion = outletRegion;
        return this;
    }

    public void setOutletRegion(String outletRegion) {
        this.outletRegion = outletRegion;
    }

    public String getOutletAdress() {
        return outletAdress;
    }

    public Outlet outletAdress(String outletAdress) {
        this.outletAdress = outletAdress;
        return this;
    }

    public void setOutletAdress(String outletAdress) {
        this.outletAdress = outletAdress;
    }

    public Double getOutletLat() {
        return outletLat;
    }

    public Outlet outletLat(Double outletLat) {
        this.outletLat = outletLat;
        return this;
    }

    public void setOutletLat(Double outletLat) {
        this.outletLat = outletLat;
    }

    public Double getOutletLng() {
        return outletLng;
    }

    public Outlet outletLng(Double outletLng) {
        this.outletLng = outletLng;
        return this;
    }

    public void setOutletLng(Double outletLng) {
        this.outletLng = outletLng;
    }

    public Set<Inventory> getInventories() {
        return inventories;
    }

    public Outlet inventories(Set<Inventory> inventories) {
        this.inventories = inventories;
        return this;
    }

    public Outlet addInventories(Inventory inventory) {
        this.inventories.add(inventory);
        inventory.setOutlet(this);
        return this;
    }

    public Outlet removeInventories(Inventory inventory) {
        this.inventories.remove(inventory);
        inventory.setOutlet(null);
        return this;
    }

    public void setInventories(Set<Inventory> inventories) {
        this.inventories = inventories;
    }

    public Set<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrders;
    }

    public Outlet purchaseOrders(Set<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
        return this;
    }

    public Outlet addPurchaseOrders(PurchaseOrder purchaseOrder) {
        this.purchaseOrders.add(purchaseOrder);
        purchaseOrder.setOutlet(this);
        return this;
    }

    public Outlet removePurchaseOrders(PurchaseOrder purchaseOrder) {
        this.purchaseOrders.remove(purchaseOrder);
        purchaseOrder.setOutlet(null);
        return this;
    }

    public void setPurchaseOrders(Set<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }

    public Set<Tickets> getTickets() {
        return tickets;
    }

    public Outlet tickets(Set<Tickets> tickets) {
        this.tickets = tickets;
        return this;
    }

    public Outlet addTickets(Tickets tickets) {
        this.tickets.add(tickets);
        tickets.setOutlet(this);
        return this;
    }

    public Outlet removeTickets(Tickets tickets) {
        this.tickets.remove(tickets);
        tickets.setOutlet(null);
        return this;
    }

    public void setTickets(Set<Tickets> tickets) {
        this.tickets = tickets;
    }

    public Set<DeviceOverviewStats> getDeviceOverviewStats() {
        return deviceOverviewStats;
    }

    public Outlet deviceOverviewStats(Set<DeviceOverviewStats> deviceOverviewStats) {
        this.deviceOverviewStats = deviceOverviewStats;
        return this;
    }

    public Outlet addDeviceOverviewStats(DeviceOverviewStats deviceOverviewStats) {
        this.deviceOverviewStats.add(deviceOverviewStats);
        deviceOverviewStats.setOutlet(this);
        return this;
    }

    public Outlet removeDeviceOverviewStats(DeviceOverviewStats deviceOverviewStats) {
        this.deviceOverviewStats.remove(deviceOverviewStats);
        deviceOverviewStats.setOutlet(null);
        return this;
    }

    public void setDeviceOverviewStats(Set<DeviceOverviewStats> deviceOverviewStats) {
        this.deviceOverviewStats = deviceOverviewStats;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Outlet)) {
            return false;
        }
        return id != null && id.equals(((Outlet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Outlet{" +
            "id=" + getId() +
            ", outletId=" + getOutletId() +
            ", outletName='" + getOutletName() + "'" +
            ", outletRegion='" + getOutletRegion() + "'" +
            ", outletAdress='" + getOutletAdress() + "'" +
            ", outletLat=" + getOutletLat() +
            ", outletLng=" + getOutletLng() +
            "}";
    }
}
