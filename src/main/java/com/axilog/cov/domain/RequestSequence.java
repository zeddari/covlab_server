package com.axilog.cov.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "request_sequence")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestSequence {

    @Id
    private Long id;
    private Long currentNumber;
    private String prefix;
}
