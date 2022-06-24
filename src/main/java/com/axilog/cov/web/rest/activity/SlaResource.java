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

import com.axilog.cov.domain.Sla;
import com.axilog.cov.repository.SlaRepository;
import com.axilog.cov.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link io.github.jhipster.sample.domain.activity.Sla}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SlaResource {

    private final Logger log = LoggerFactory.getLogger(SlaResource.class);

    private static final String ENTITY_NAME = "sla";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SlaRepository slaRepository;

    public SlaResource(SlaRepository slaRepository) {
        this.slaRepository = slaRepository;
    }

    /**
     * {@code POST  /slas} : Create a new sla.
     *
     * @param sla the sla to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sla, or with status {@code 400 (Bad Request)} if the sla has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/slas")
    public ResponseEntity<Sla> createSla(@RequestBody Sla sla) throws URISyntaxException {
        log.debug("REST request to save Sla : {}", sla);
        if (sla.getId() != null) {
            throw new BadRequestAlertException("A new sla cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sla result = slaRepository.save(sla);
        return ResponseEntity.created(new URI("/api/slas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /slas} : Updates an existing sla.
     *
     * @param sla the sla to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sla,
     * or with status {@code 400 (Bad Request)} if the sla is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sla couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/slas")
    public ResponseEntity<Sla> updateSla(@RequestBody Sla sla) throws URISyntaxException {
        log.debug("REST request to update Sla : {}", sla);
        if (sla.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Sla result = slaRepository.save(sla);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sla.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /slas} : get all the slas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of slas in body.
     */
    @GetMapping("/slas")
    public List<Sla> getAllSlas() {
        log.debug("REST request to get all Slas");
        return slaRepository.findAll();
    }

    /**
     * {@code GET  /slas/:id} : get the "id" sla.
     *
     * @param id the id of the sla to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sla, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/slas/{id}")
    public ResponseEntity<Sla> getSla(@PathVariable Long id) {
        log.debug("REST request to get Sla : {}", id);
        Optional<Sla> sla = slaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sla);
    }

    /**
     * {@code DELETE  /slas/:id} : delete the "id" sla.
     *
     * @param id the id of the sla to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/slas/{id}")
    public ResponseEntity<Void> deleteSla(@PathVariable Long id) {
        log.debug("REST request to delete Sla : {}", id);
        slaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
