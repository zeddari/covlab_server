package com.axilog.cov.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "po_seq")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sequence {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long currentNumber;

}
