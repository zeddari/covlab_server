package com.axilog.cov.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axilog.cov.domain.Task;
import com.axilog.cov.domain.Task_;
import com.axilog.cov.dto.criteria.TaskCriteria;
import com.axilog.cov.repository.TaskRepository;
import com.axilog.cov.repository.search.TaskSearchRepository;

import io.github.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Task} entities in the database.
 * The main input is a {@link TaskCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Task} or a {@link Page} of {@link Task} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TaskQueryService extends QueryService<Task> {

    private final Logger log = LoggerFactory.getLogger(TaskQueryService.class);

    private final TaskRepository taskRepository;

    private final TaskSearchRepository taskSearchRepository;

    public TaskQueryService(TaskRepository taskRepository, TaskSearchRepository taskSearchRepository) {
        this.taskRepository = taskRepository;
        this.taskSearchRepository = taskSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Task} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Task> findByCriteria(TaskCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Task> specification = createSpecification(criteria);
        return taskRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Task} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Task> findByCriteria(TaskCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Task> specification = createSpecification(criteria);
        return taskRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TaskCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Task> specification = createSpecification(criteria);
        return taskRepository.count(specification);
    }

    /**
     * Function to convert {@link TaskCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Task> createSpecification(TaskCriteria criteria) {
        Specification<Task> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Task_.id));
            }
            if (criteria.getTaskId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaskId(), Task_.taskId));
            }
            if (criteria.getPriority() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPriority(), Task_.priority));
            }
            if (criteria.getDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDueDate(), Task_.dueDate));
            }
            if (criteria.getProcessInstanceId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProcessInstanceId(), Task_.processInstanceId));
            }
            if (criteria.getAssignee() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssignee(), Task_.assignee));
            }
            if (criteria.getRequestId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRequestId(), Task_.requestId));
            }
            if (criteria.getEquipName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEquipName(), Task_.equipName));
            }
        }
        return specification;
    }
}
