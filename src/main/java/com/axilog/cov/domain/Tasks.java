package com.axilog.cov.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.Data;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Tasks.
 */
@Entity
@Table(name = "tasks")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class Tasks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_id")
    private String taskId;

    @Column(name = "priority")
    private Long priority;

    @Column(name = "due_date")
    private Instant dueDate;

    @Column(name = "process_instance_id")
    private String processInstanceId;

    @Column(name = "assignee")
    private String assignee;

    @Column(name = "application_id")
    private String requestId;

    @Column(name = "equip_name")
    private String equipName;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public Tasks taskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Long getPriority() {
        return priority;
    }

    public Tasks priority(Long priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public Instant getDueDate() {
        return dueDate;
    }

    public Tasks dueDate(Instant dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public Tasks processInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getAssignee() {
        return assignee;
    }

    public Tasks assignee(String assignee) {
        this.assignee = assignee;
        return this;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getRequestId() {
        return requestId;
    }

    public Tasks requestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getEquipName() {
        return equipName;
    }

    public Tasks equipName(String equipName) {
        this.equipName = equipName;
        return this;
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tasks)) {
            return false;
        }
        return id != null && id.equals(((Tasks) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tasks{" +
            "id=" + getId() +
            ", taskId='" + getTaskId() + "'" +
            ", priority=" + getPriority() +
            ", dueDate='" + getDueDate() + "'" +
            ", processInstanceId='" + getProcessInstanceId() + "'" +
            ", assignee='" + getAssignee() + "'" +
            ", applicationId='" + getRequestId() + "'" +
            ", equipName='" + getEquipName() + "'" +
            "}";
    }
}
