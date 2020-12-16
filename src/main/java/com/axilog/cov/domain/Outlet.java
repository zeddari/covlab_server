package com.axilog.cov.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

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
    private String outletId;

    @Column(name = "outlet_name")
    private String outletName;

    @Column(name = "outlet_location")
    private String outletLocation;

    @Column(name = "outlet_lat")
    private String outletLat;

    @Column(name = "outlet_long")
    private String outletLong;

    @OneToMany(mappedBy = "outlet")
    private Set<Inventory> inventories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutletId() {
        return outletId;
    }

    public Outlet outletId(String outletId) {
        this.outletId = outletId;
        return this;
    }

    public void setOutletId(String outletId) {
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

    public String getOutletLocation() {
        return outletLocation;
    }

    public Outlet outletLocation(String outletLocation) {
        this.outletLocation = outletLocation;
        return this;
    }

    public void setOutletLocation(String outletLocation) {
        this.outletLocation = outletLocation;
    }

    public String getOutletLat() {
        return outletLat;
    }

    public Outlet outletLat(String outletLat) {
        this.outletLat = outletLat;
        return this;
    }

    public void setOutletLat(String outletLat) {
        this.outletLat = outletLat;
    }

    public String getOutletLong() {
        return outletLong;
    }

    public Outlet outletLong(String outletLong) {
        this.outletLong = outletLong;
        return this;
    }

    public void setOutletLong(String outletLong) {
        this.outletLong = outletLong;
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
            ", outletId='" + getOutletId() + "'" +
            ", outletName='" + getOutletName() + "'" +
            ", outletLocation='" + getOutletLocation() + "'" +
            ", outletLat='" + getOutletLat() + "'" +
            ", outletLong='" + getOutletLong() + "'" +
            "}";
    }
}
