package com.axilog.cov.domain;

import java.io.Serializable;
import java.util.Date;

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
 * A Product.
 */
@Entity
@Table(name = "import_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "file_name")
    private String fileName;
    
    @Column(name = "job_id")
    private String jobId;
   
    @Column(name = "outlet")
    private String outlet;
    
    @Column(name = "imported_at")
    private Date importedAt;
    
    @Column(name = "imported_by")
    private String imported_by;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "message")
    private String message;
    
    @Column(name = "nupco_code")
    private String nupcoCode;
    
    @Column(name = "result")
    private String result;
    
}
