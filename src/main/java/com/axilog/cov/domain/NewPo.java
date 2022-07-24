package com.axilog.cov.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A new po.
 */
@Entity
@Table(name = "new_po")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "po_id")
    private String poId;

    @Column(name = "quotation_id")
    private String quotationId;

    @Column(name = "po_amount")
    private Long poAmount;

}
