package com.axilog.cov.web.rest.activity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axilog.cov.domain.RequestStatus;
import com.axilog.cov.repository.RequestStatusRepository;
import com.axilog.cov.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link io.github.jhipster.sample.domain.activity.RequestStatus}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RequestStatusResource {

    private final Logger log = LoggerFactory.getLogger(RequestStatusResource.class);

    private static final String ENTITY_NAME = "requestStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestStatusRepository requestStatusRepository;

    public RequestStatusResource(RequestStatusRepository requestStatusRepository) {
        this.requestStatusRepository = requestStatusRepository;
    }

    /**
     * {@code POST  /request-statuses} : Create a new requestStatus.
     *
     * @param requestStatus the requestStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestStatus, or with status {@code 400 (Bad Request)} if the requestStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/request-statuses")
    public ResponseEntity<RequestStatus> createRequestStatus(@RequestBody RequestStatus requestStatus) throws URISyntaxException {
        log.debug("REST request to save RequestStatus : {}", requestStatus);
        if (requestStatus.getId() != null) {
            throw new BadRequestAlertException("A new requestStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequestStatus result = requestStatusRepository.save(requestStatus);
        return ResponseEntity.created(new URI("/api/request-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /request-statuses} : Updates an existing requestStatus.
     *
     * @param requestStatus the requestStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestStatus,
     * or with status {@code 400 (Bad Request)} if the requestStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/request-statuses")
    public ResponseEntity<RequestStatus> updateRequestStatus(@RequestBody RequestStatus requestStatus) throws URISyntaxException {
        log.debug("REST request to update RequestStatus : {}", requestStatus);
        if (requestStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RequestStatus result = requestStatusRepository.save(requestStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requestStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /request-statuses} : get all the requestStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestStatuses in body.
     */
    @GetMapping("/request-statuses")
    public List<RequestStatus> getAllRequestStatuses() {
        log.debug("REST request to get all RequestStatuses");
        return requestStatusRepository.findAll();
    }

    /**
     * {@code GET  /request-statuses/:id} : get the "id" requestStatus.
     *
     * @param id the id of the requestStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/request-statuses/{id}")
    public ResponseEntity<RequestStatus> getRequestStatus(@PathVariable Long id) {
        log.debug("REST request to get RequestStatus : {}", id);
        Optional<RequestStatus> requestStatus = requestStatusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(requestStatus);
    }

    /**
     * {@code DELETE  /request-statuses/:id} : delete the "id" requestStatus.
     *
     * @param id the id of the requestStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/request-statuses/{id}")
    public ResponseEntity<Void> deleteRequestStatus(@PathVariable Long id) {
        log.debug("REST request to delete RequestStatus : {}", id);
        requestStatusRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
