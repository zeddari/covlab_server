package com.axilog.cov.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Outlet.
 */
@Entity
@Table(name = "outlet")
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

    @OneToMany(mappedBy = "outlet")
    private Set<Inventory> inventories = new HashSet<>();

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

    public Outlet addInventory(Inventory inventory) {
        this.inventories.add(inventory);
        inventory.setOutlet(this);
        return this;
    }

    public Outlet removeInventory(Inventory inventory) {
        this.inventories.remove(inventory);
        inventory.setOutlet(null);
        return this;
    }

    public void setInventories(Set<Inventory> inventories) {
        this.inventories = inventories;
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
