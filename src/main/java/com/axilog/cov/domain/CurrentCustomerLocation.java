package com.axilog.cov.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Category.
 */
@Entity
@Table(name = "current_customer_locations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentCustomerLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quotation_id")
    private String quotationId;

    @Column(name = "lat")
    private String lat;

    @Column(name = "lng")
    private String lng;


}
