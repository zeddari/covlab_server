package com.axilog.cov.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.axilog.cov.domain.Inventory;
import com.axilog.cov.domain.Outlet;
import com.axilog.cov.domain.Product;
import com.axilog.cov.dto.command.InventoryHistoryCommand;
import com.axilog.cov.dto.command.SelectCommand;
import com.axilog.cov.dto.mapper.InventoryMapper;
import com.axilog.cov.dto.representation.InventoryRepresentation;
import com.axilog.cov.dto.representation.OutletRepresentation;
import com.axilog.cov.service.OutletQueryService;
import com.axilog.cov.service.OutletService;
import com.axilog.cov.service.dto.OutletCriteria;
import com.axilog.cov.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;

/**
 * REST controller for managing {@link com.axilog.cov.domain.Outlet}.
 */
@RestController
@RequestMapping("/api")
@Api(tags = "Inventory Management", value = "InventoryManagement", description = "Controller for Inventory Management")

public class OutletResource {

    private final Logger log = LoggerFactory.getLogger(OutletResource.class);

    private static final String ENTITY_NAME = "outlet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
   
    @Autowired
    private  OutletService outletService;
   
    @Autowired
    private  OutletQueryService outletQueryService;
    
    @Autowired
    private InventoryMapper inventoryMapper;

    public OutletResource(OutletService outletService, OutletQueryService outletQueryService) {
        this.outletService = outletService;
        this.outletQueryService = outletQueryService;
    }

    /**
     * {@code POST  /outlets} : Create a new outlet.
     *
     * @param outlet the outlet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new outlet, or with status {@code 400 (Bad Request)} if the outlet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/outlets")
    public ResponseEntity<Outlet> createOutlet(@RequestBody Outlet outlet) throws URISyntaxException {
        log.debug("REST request to save Outlet : {}", outlet);
        List <Outlet> listoutlet = outletService.findByOuletName(outlet.getOutletName());
        
        	 if (outlet.getId() != null || listoutlet != null) {
                 throw new BadRequestAlertException("A new outlet cannot already ", ENTITY_NAME, "exists");
             }  else {
        	 Outlet result = outletService.save(outlet);
		            
        return ResponseEntity.created(new URI("/api/outlets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
             
            }
    }

    /**
     * {@code PUT  /outlets} : Updates an existing outlet.
     *
     * @param outlet the outlet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated outlet,
     * or with status {@code 400 (Bad Request)} if the outlet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the outlet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/outlets")
    public ResponseEntity<Outlet> updateOutlet(@RequestBody Outlet outlet) throws URISyntaxException {
        log.debug("REST request to update Outlet : {}", outlet);
        if (outlet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Outlet result = outletService.save(outlet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, outlet.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /outlets} : get all the outlets.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of outlets in body.
     */
    @GetMapping("/outlets")
    public ResponseEntity<List<Outlet>> getAllOutlets(OutletCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Outlets by criteria: {}", criteria);
        List<Outlet> oulets = outletService.findAll();
        return ResponseEntity.ok().body(oulets);
    }

    /**
     * {@code GET  /outlets/count} : count all the outlets.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/outlets/count")
    public ResponseEntity<Long> countOutlets(OutletCriteria criteria) {
        log.debug("REST request to count Outlets by criteria: {}", criteria);
        return ResponseEntity.ok().body(outletQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /outlets/:id} : get the "id" outlet.
     *
     * @param id the id of the outlet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the outlet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/outlets/{id}")
    public ResponseEntity<Outlet> getOutlet(@PathVariable Long id) {
        log.debug("REST request to get Outlet : {}", id);
        Optional<Outlet> outlet = outletService.findOne(id);
        return ResponseUtil.wrapOrNotFound(outlet);
    }

    /**
     * {@code DELETE  /outlets/:id} : delete the "id" outlet.
     *
     * @param id the id of the outlet to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/outlets/{id}")
    public ResponseEntity<Void> deleteOutlet(@PathVariable Long id) {
        log.debug("REST request to delete Outlet : {}", id);
        outletService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    @PostMapping("/outlets/region")
    public ResponseEntity<OutletRepresentation> getOutletRegion(@RequestBody @Valid SelectCommand selectCommand) {
        log.debug("REST request to get Inventories by FindOutletByRegion: {}");
        
        List<Outlet> outlets = outletService.findOutletParentRegion(selectCommand.getParentRegion());
        OutletRepresentation outletRepresentation = inventoryMapper.toOutletRepresentation(outlets);
        return ResponseEntity.ok().body(outletRepresentation);
    }
}
