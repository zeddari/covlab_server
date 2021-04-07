package com.axilog.cov.domain;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Category.
 */
@Entity
@Table(name = "substitute")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Substitute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "substitutes", allowSetters = true)
    private Product product;
    
    @Column(name = "impact_factor")
    private Double impactFactor;

    @Column(name = "substitute_code")
    private String substituteCode;

    @Column(name = "substitute_category")
    private String substituteCategory;

    @Column(name = "substitute_description")
    private String substituteDescription;
}
