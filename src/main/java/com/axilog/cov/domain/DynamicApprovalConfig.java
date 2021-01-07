package com.axilog.cov.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Tickets.
 */
@Entity
@Table(name = "dynamic_approval_config")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DynamicApprovalConfig implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "current_step")
    private String currentStep;

    @Column(name = "current_step_email")
    private String currentStepEmail;

    @Column(name = "current_step_status")
    private String currentStepStatus;

    @Column(name = "next_step")
    private String nextStep;

    @Column(name = "next_step_status")
    private String nextStepStatus;

    @Column(name = "start_status")
    private Boolean startStatus;

    @Column(name = "final_status")
    private Boolean finalStatus;

   
}
