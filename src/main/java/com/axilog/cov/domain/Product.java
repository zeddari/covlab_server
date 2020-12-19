package com.axilog.cov.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "description")
    private String description;

    @Column(name = "product_code")
    private String productCode;

    @OneToMany(mappedBy = "product")
    private Set<Inventory> inventories = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<PurchaseOrder> purchaseOrders = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<Tickets> tickets = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "products", allowSetters = true)
    private Category category;

    @ManyToOne
    @JsonIgnoreProperties(value = "products", allowSetters = true)
    private DeviceOverviewStats deviceOverviewStats;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public Product productId(Long productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getDescription() {
        return description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductCode() {
        return productCode;
    }

    public Product productCode(String productCode) {
        this.productCode = productCode;
        return this;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Set<Inventory> getInventories() {
        return inventories;
    }

    public Product inventories(Set<Inventory> inventories) {
        this.inventories = inventories;
        return this;
    }

    public Product addInventories(Inventory inventory) {
        this.inventories.add(inventory);
        inventory.setProduct(this);
        return this;
    }

    public Product removeInventories(Inventory inventory) {
        this.inventories.remove(inventory);
        inventory.setProduct(null);
        return this;
    }

    public void setInventories(Set<Inventory> inventories) {
        this.inventories = inventories;
    }

    public Set<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrders;
    }

    public Product purchaseOrders(Set<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
        return this;
    }

    public Product addPurchaseOrders(PurchaseOrder purchaseOrder) {
        this.purchaseOrders.add(purchaseOrder);
        purchaseOrder.setProduct(this);
        return this;
    }

    public Product removePurchaseOrders(PurchaseOrder purchaseOrder) {
        this.purchaseOrders.remove(purchaseOrder);
        purchaseOrder.setProduct(null);
        return this;
    }

    public void setPurchaseOrders(Set<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }

    public Set<Tickets> getTickets() {
        return tickets;
    }

    public Product tickets(Set<Tickets> tickets) {
        this.tickets = tickets;
        return this;
    }

    public Product addTickets(Tickets tickets) {
        this.tickets.add(tickets);
        tickets.setProduct(this);
        return this;
    }

    public Product removeTickets(Tickets tickets) {
        this.tickets.remove(tickets);
        tickets.setProduct(null);
        return this;
    }

    public void setTickets(Set<Tickets> tickets) {
        this.tickets = tickets;
    }

    public Category getCategory() {
        return category;
    }

    public Product category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public DeviceOverviewStats getDeviceOverviewStats() {
        return deviceOverviewStats;
    }

    public Product deviceOverviewStats(DeviceOverviewStats deviceOverviewStats) {
        this.deviceOverviewStats = deviceOverviewStats;
        return this;
    }

    public void setDeviceOverviewStats(DeviceOverviewStats deviceOverviewStats) {
        this.deviceOverviewStats = deviceOverviewStats;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", productId=" + getProductId() +
            ", description='" + getDescription() + "'" +
            ", productCode='" + getProductCode() + "'" +
            "}";
    }
}
