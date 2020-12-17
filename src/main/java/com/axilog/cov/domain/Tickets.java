package com.axilog.cov.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Tickets.
 */
@Entity
@Table(name = "tickets")
public class Tickets implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticket_no")
    private Long ticketNo;

    @Column(name = "ticket_type")
    private String ticketType;

    @Column(name = "ticket_status")
    private String ticketStatus;

    @Column(name = "ticket_due_date")
    private LocalDate ticketDueDate;

    @Column(name = "ticket_priority")
    private String ticketPriority;

    @Column(name = "ticket_created_on")
    private LocalDate ticketCreatedOn;

    @Column(name = "ticket_update_at")
    private LocalDate ticketUpdateAt;

    @ManyToOne
    @JsonIgnoreProperties(value = "tickets", allowSetters = true)
    private Outlet outlet;

    @ManyToOne
    @JsonIgnoreProperties(value = "tickets", allowSetters = true)
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTicketNo() {
        return ticketNo;
    }

    public Tickets ticketNo(Long ticketNo) {
        this.ticketNo = ticketNo;
        return this;
    }

    public void setTicketNo(Long ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getTicketType() {
        return ticketType;
    }

    public Tickets ticketType(String ticketType) {
        this.ticketType = ticketType;
        return this;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public Tickets ticketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
        return this;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public LocalDate getTicketDueDate() {
        return ticketDueDate;
    }

    public Tickets ticketDueDate(LocalDate ticketDueDate) {
        this.ticketDueDate = ticketDueDate;
        return this;
    }

    public void setTicketDueDate(LocalDate ticketDueDate) {
        this.ticketDueDate = ticketDueDate;
    }

    public String getTicketPriority() {
        return ticketPriority;
    }

    public Tickets ticketPriority(String ticketPriority) {
        this.ticketPriority = ticketPriority;
        return this;
    }

    public void setTicketPriority(String ticketPriority) {
        this.ticketPriority = ticketPriority;
    }

    public LocalDate getTicketCreatedOn() {
        return ticketCreatedOn;
    }

    public Tickets ticketCreatedOn(LocalDate ticketCreatedOn) {
        this.ticketCreatedOn = ticketCreatedOn;
        return this;
    }

    public void setTicketCreatedOn(LocalDate ticketCreatedOn) {
        this.ticketCreatedOn = ticketCreatedOn;
    }

    public LocalDate getTicketUpdateAt() {
        return ticketUpdateAt;
    }

    public Tickets ticketUpdateAt(LocalDate ticketUpdateAt) {
        this.ticketUpdateAt = ticketUpdateAt;
        return this;
    }

    public void setTicketUpdateAt(LocalDate ticketUpdateAt) {
        this.ticketUpdateAt = ticketUpdateAt;
    }

    public Outlet getOutlet() {
        return outlet;
    }

    public Tickets outlet(Outlet outlet) {
        this.outlet = outlet;
        return this;
    }

    public void setOutlet(Outlet outlet) {
        this.outlet = outlet;
    }

    public Product getProduct() {
        return product;
    }

    public Tickets product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tickets)) {
            return false;
        }
        return id != null && id.equals(((Tickets) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tickets{" +
            "id=" + getId() +
            ", ticketNo=" + getTicketNo() +
            ", ticketType='" + getTicketType() + "'" +
            ", ticketStatus='" + getTicketStatus() + "'" +
            ", ticketDueDate='" + getTicketDueDate() + "'" +
            ", ticketPriority='" + getTicketPriority() + "'" +
            ", ticketCreatedOn='" + getTicketCreatedOn() + "'" +
            ", ticketUpdateAt='" + getTicketUpdateAt() + "'" +
            "}";
    }
}
