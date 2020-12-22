package com.axilog.cov.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.axilog.cov.dto.command.InventoryCommand;
import com.axilog.cov.dto.mapper.InventoryMapper;
import com.axilog.cov.dto.representation.InventoryRepresentation;
import com.axilog.cov.service.InventoryQueryService;
import com.axilog.cov.service.InventoryService;
import com.axilog.cov.service.dto.InventoryCriteria;
import com.axilog.cov.util.DateUtil;
import com.axilog.cov.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;

/**
 * REST controller for managing {@link com.axilog.cov.domain.Inventory}.
 */
@RestController
@RequestMapping("/api")
@Api(tags = "Inventory Management", value = "InventoryManagement", description = "Controller for Inventory Management")

public class InventoryResource {

    private final Logger log = LoggerFactory.getLogger(InventoryResource.class);

    private static final String ENTITY_NAME = "inventory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InventoryService inventoryService;

    private final InventoryQueryService inventoryQueryService;

    @Autowired
    private InventoryMapper inventoryMapper;
    
    
    public InventoryResource(InventoryService inventoryService, InventoryQueryService inventoryQueryService) {
        this.inventoryService = inventoryService;
        this.inventoryQueryService = inventoryQueryService;
    }

    /**
     * {@code POST  /inventories} : Create a new inventory.
     *
     * @param inventory the inventory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inventory, or with status {@code 400 (Bad Request)} if the inventory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inventories")
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) throws URISyntaxException {
        log.debug("REST request to save Inventory : {}", inventory);
        if (inventory.getId() != null) {
            throw new BadRequestAlertException("A new inventory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Inventory result = inventoryService.save(inventory);
        return ResponseEntity.created(new URI("/api/inventories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inventories} : Updates an existing inventory.
     *
     * @param inventory the inventory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventory,
     * or with status {@code 400 (Bad Request)} if the inventory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inventory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inventories")
    public ResponseEntity<Inventory> updateInventory(@RequestBody Inventory inventory) throws URISyntaxException {
        log.debug("REST request to update Inventory : {}", inventory);
        if (inventory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Inventory result = inventoryService.save(inventory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inventory.getId().toString()))
            .body(result);
    }

    @PutMapping("/inventory/update")
    public ResponseEntity<Inventory> updateInventoryByCriteria(@RequestBody InventoryCommand inventoryCommand) throws URISyntaxException {
        log.debug("REST request to update Inventory : {}", inventoryCommand);
        if (inventoryCommand.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Optional<Inventory> inventoryOptional = inventoryService.findOne(inventoryCommand.getId());
        if (!inventoryOptional.isPresent()) {
        	throw new BadRequestAlertException("Id Does not Exist", ENTITY_NAME, "idnull");
        }
        Inventory result = inventoryOptional.get();
        result.setQuantitiesInHand(inventoryCommand.getQuantitiesInHand());
        result.setQuantitiesInTransit(inventoryCommand.getQuantitiesInTransit());
        result.setLastUpdatedAt(DateUtil.now());
        result = inventoryService.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    
    @PutMapping("/inventory/update/new")
    public ResponseEntity<Inventory> updateNewInventoryByCriteria(@RequestBody InventoryCommand inventoryCommand) throws URISyntaxException {
        log.debug("REST request to update Inventory : {}", inventoryCommand);
        if (inventoryCommand.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Optional<Inventory> inventoryOptional = inventoryService.findOne(inventoryCommand.getId());
        if (!inventoryOptional.isPresent()) {
        	throw new BadRequestAlertException("Id Does not Exist", ENTITY_NAME, "idnull");
        }
        Inventory result = inventoryOptional.get();
        result.setIsLastInstance(Boolean.FALSE);
        result = inventoryService.save(result);
        
        //create new entry with new date
        result.setQuantitiesInHand(inventoryCommand.getQuantitiesInHand());
        result.setActualDailyConsumption(inventoryCommand.getActualDailyConsumption());
        result.setLastUpdatedAt(DateUtil.now());
        result.setId(null);
        result.setIsLastInstance(Boolean.TRUE);
        result = inventoryService.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    /**
     * {@code GET  /inventories} : get all the inventories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inventories in body.
     */
    @GetMapping("/inventories")
    public ResponseEntity<List<Inventory>> getAllInventories(InventoryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Inventories by criteria: {}", criteria);
        Page<Inventory> page = inventoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/inventory/list")
    public ResponseEntity<InventoryRepresentation> getAllRepresentationInventories(InventoryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Inventories by criteria: {}", criteria);
        List<Inventory> inventories = inventoryQueryService.findByCriteria(criteria);
        inventories = inventories.stream()
                	.filter(inventory -> inventory.getIsLastInstance().equals(Boolean.TRUE)) // 
                	.collect(Collectors.toList());
        
        //sort descending by lastUpdated
        inventories = inventories.stream().sorted(Comparator.comparing(Inventory::getLastUpdatedAt, Comparator.nullsLast(Comparator.reverseOrder()))).collect(Collectors.toList());
        InventoryRepresentation inventoryRepresentation = inventoryMapper.toInventoryRepresentation(inventories);
        return ResponseEntity.ok().body(inventoryRepresentation);
    }
    /**
     * {@code GET  /inventories/count} : count all the inventories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/inventories/count")
    public ResponseEntity<Long> countInventories(InventoryCriteria criteria) {
        log.debug("REST request to count Inventories by criteria: {}", criteria);
        return ResponseEntity.ok().body(inventoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /inventories/:id} : get the "id" inventory.
     *
     * @param id the id of the inventory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inventory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inventories/{id}")
    public ResponseEntity<Inventory> getInventory(@PathVariable Long id) {
        log.debug("REST request to get Inventory : {}", id);
        Optional<Inventory> inventory = inventoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inventory);
    }

    /**
     * {@code DELETE  /inventories/:id} : delete the "id" inventory.
     *
     * @param id the id of the inventory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inventories/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        log.debug("REST request to delete Inventory : {}", id);
        inventoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
