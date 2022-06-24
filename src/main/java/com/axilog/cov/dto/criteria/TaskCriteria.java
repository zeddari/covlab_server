package com.axilog.cov.dto.criteria;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.axilog.myflow.domain.Task} entity. This class is used
 * in {@link com.axilog.myflow.web.rest.TaskResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tasks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TaskCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter taskId;

    private LongFilter priority;

    private InstantFilter dueDate;

    private StringFilter processInstanceId;

    private StringFilter assignee;

    private StringFilter requestId;

    private StringFilter equipName;

    public TaskCriteria() {
    }

    public TaskCriteria(TaskCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.taskId = other.taskId == null ? null : other.taskId.copy();
        this.priority = other.priority == null ? null : other.priority.copy();
        this.dueDate = other.dueDate == null ? null : other.dueDate.copy();
        this.processInstanceId = other.processInstanceId == null ? null : other.processInstanceId.copy();
        this.assignee = other.assignee == null ? null : other.assignee.copy();
        this.requestId = other.requestId == null ? null : other.requestId.copy();
        this.equipName = other.equipName == null ? null : other.equipName.copy();
    }

    @Override
    public TaskCriteria copy() {
        return new TaskCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTaskId() {
        return taskId;
    }

    public void setTaskId(StringFilter taskId) {
        this.taskId = taskId;
    }

    public LongFilter getPriority() {
        return priority;
    }

    public void setPriority(LongFilter priority) {
        this.priority = priority;
    }

    public InstantFilter getDueDate() {
        return dueDate;
    }

    public void setDueDate(InstantFilter dueDate) {
        this.dueDate = dueDate;
    }

    public StringFilter getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(StringFilter processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public StringFilter getAssignee() {
        return assignee;
    }

    public void setAssignee(StringFilter assignee) {
        this.assignee = assignee;
    }

    public StringFilter getRequestId() {
        return requestId;
    }

    public void setRequestId(StringFilter requestId) {
        this.requestId = requestId;
    }

    public StringFilter getEquipName() {
        return equipName;
    }

    public void setEquipName(StringFilter equipName) {
        this.equipName = equipName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TaskCriteria that = (TaskCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(taskId, that.taskId) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(dueDate, that.dueDate) &&
            Objects.equals(processInstanceId, that.processInstanceId) &&
            Objects.equals(assignee, that.assignee) &&
            Objects.equals(requestId, that.requestId) &&
            Objects.equals(equipName, that.equipName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        taskId,
        priority,
        dueDate,
        processInstanceId,
        assignee,
        requestId,
        equipName
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (taskId != null ? "taskId=" + taskId + ", " : "") +
                (priority != null ? "priority=" + priority + ", " : "") +
                (dueDate != null ? "dueDate=" + dueDate + ", " : "") +
                (processInstanceId != null ? "processInstanceId=" + processInstanceId + ", " : "") +
                (assignee != null ? "assignee=" + assignee + ", " : "") +
                (requestId != null ? "requestId=" + requestId + ", " : "") +
                (equipName != null ? "equipName=" + equipName + ", " : "") +
            "}";
    }

}
