package com.axilog.cov.domain;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "substitute_code")
    private String substituteCode;

    @Column(name = "substitute_label")
    private String substituteLabel;

}
