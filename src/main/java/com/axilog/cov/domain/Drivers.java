package com.axilog.cov.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * A DRIVERS.
 */
@Entity
@Table(name = "drivers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Drivers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_drivers")
    private Long idDrivers;

   
    @Column(name = "name_drivers")
    private String nameDrivers;


	public Object getIdDrivers() {
		// TODO Auto-generated method stub
		return null;
	}

    
}
