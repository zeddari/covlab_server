package com.axilog.cov.web.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.axilog.cov.domain.ImportHistory;
import com.axilog.cov.domain.Inventory;
import com.axilog.cov.domain.Outlet;
import com.axilog.cov.domain.PoStatus;
import com.axilog.cov.domain.Product;
import com.axilog.cov.dto.command.InventoryCommand;
import com.axilog.cov.dto.command.InventoryHistoryCommand;
import com.axilog.cov.dto.mapper.InventoryMapper;
import com.axilog.cov.dto.representation.InventoryRepresentation;
import com.axilog.cov.security.SecurityUtils;
import com.axilog.cov.service.ImportHistoryService;
import com.axilog.cov.service.InventoryQueryService;
import com.axilog.cov.service.InventoryService;
import com.axilog.cov.service.OutletService;
import com.axilog.cov.service.ProductService;
import com.axilog.cov.service.dto.InventoryCriteria;
import com.axilog.cov.util.DateUtil;
import com.axilog.cov.util.XlsxFileUtil;
import com.axilog.cov.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;
import liquibase.pro.packaged.iN;

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
    
    @Autowired
    private OutletService outletService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private ImportHistoryService importHistoryService;
    
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
        if (inventory.getLastUpdatedAt() == null) inventory.setLastUpdatedAt(DateUtil.now());
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
        result.setReceivedQty(inventoryCommand.getCurrentBalance());
        result.setQuantitiesInTransit(inventoryCommand.getQuantitiesInTransit());
        result.setLastUpdatedAt(DateUtil.now());
        result = inventoryService.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    @PutMapping("/inventory/updateConsumedQty")
    public ResponseEntity<Inventory> updateInventoryByConsumedQty(@RequestBody InventoryCommand inventoryCommand) throws URISyntaxException {
        log.debug("REST request to update Inventory : {}", inventoryCommand);
        if (inventoryCommand.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Optional<Inventory> inventoryOptional = inventoryService.findOne(inventoryCommand.getId());
        if (!inventoryOptional.isPresent()) {
        	throw new BadRequestAlertException("Id Does not Exist", ENTITY_NAME, "idnull");
        }
        Inventory result = inventoryOptional.get();
        result.setConsumedQty(inventoryCommand.getConsumeQty());
        result = inventoryService.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    
    @PostMapping(value ="/inventory/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateInventoryFromXlsFile(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "outlet", required = false) String outlet) throws URISyntaxException, IOException {
        log.debug("REST request to upload Inventory : {}");
        String currentUser = SecurityUtils.getCurrentUserLogin().get();
        String jobId = Long.toString(DateUtil.uniqueCurrentTimeMS());
        if (file == null) {
            throw new BadRequestAlertException("Corrupted File", ENTITY_NAME, "Please upload correct file");
        }
        Map<Integer, List<String>> data = XlsxFileUtil.readFileFromStream(file.getInputStream(), 2);
        if (data == null || data.isEmpty()) {
        	 throw new BadRequestAlertException("Empty file", ENTITY_NAME, "Please upload a completed excel file");
        }
        Optional<Outlet> outletOpt = outletService.findByExample(Example.of(Outlet.builder().outletName(outlet).build()));
    	if (!outletOpt.isPresent()) {
    		throw new BadRequestAlertException("Bad Outlet", ENTITY_NAME, "The selected outlet does not exist");
    	}
    	
    	data.forEach((index, list) -> {
    		try {
    			String nupcoCode = list.get(1);
        		Double consumedQuantity = Double.parseDouble(list.get(6));
        		Optional<Product> productOpt = productService.findOne(Example.of(Product.builder().productCode(nupcoCode).build()));
            	if (!productOpt.isPresent()) {
            		ImportHistory importHistory = ImportHistory.builder()
            				.importedAt(DateUtil.now())
            				.imported_by(currentUser)
            				.nupcoCode(nupcoCode)
            				.result("NOK")
            				.status("PARTIAL")
            				.fileName(file.getOriginalFilename())
            				.message("Nupco code does not exist")
            				.jobId(jobId)
            				.outlet(outlet)
            				.build();
            		importHistoryService.save(importHistory);
            		//throw new BadRequestAlertException("Bad NupcoCode", ENTITY_NAME, "NupcoCode does not exist: "+nupcoCode);
            	}
            	else {
            		Example<Inventory> exampleInventory = Example.of(Inventory.builder()
            				.outlet(outletOpt.get())
            				.product(productOpt.get())
            				.isLastInstance(true)
            				.build());
                    //Optional<Inventory> inventoryOptional = inventoryService.findByExample(exampleInventory);
            		List<Inventory> inventories = inventoryService.findByOutletAndProductAndIsLastInstance(outletOpt.get(), productOpt.get(), Boolean.TRUE);
                    if (inventories == null || inventories.isEmpty()) {
                    	ImportHistory importHistory = ImportHistory.builder()
                				.importedAt(DateUtil.now())
                				.imported_by(currentUser)
                				.nupcoCode(nupcoCode)
                				.result("NOK")
                				.status("PARTIAL")
                				.fileName(file.getOriginalFilename())
                				.message("No inventory item exist for this nupco code")
                				.jobId(jobId)
                				.outlet(outlet)
                				.build();
                		importHistoryService.save(importHistory);
                    }
                    else {
                    	//save current ine with lastInstance as false
                        Inventory result = inventories.get(0);
                        result.setLastUpdatedAt(DateUtil.now());
                        result.setIsLastInstance(Boolean.FALSE);
                        result = inventoryService.save(result);
                        
                        //create new one as last instance
                        result.setCurrent_balance(result.getCurrent_balance() - consumedQuantity);
                        result.setConsumedQty(result.getConsumedQty() - consumedQuantity);
                        result.setId(null);
                        result.setIsLastInstance(Boolean.TRUE);
                        result = inventoryService.save(result);
                        
                        ImportHistory importHistory = ImportHistory.builder()
                				.importedAt(DateUtil.now())
                				.imported_by(currentUser)
                				.nupcoCode(nupcoCode)
                				.status("COMPLETED")
                				.result("OK")
                				.fileName(file.getOriginalFilename())
                				.message("Update done with success for this nupco Code")
                				.jobId(jobId)
                				.outlet(outlet)
                				.build();
                        importHistoryService.save(importHistory);
                    }
                    
            	}
    		}
    		catch(Exception e) {
    			ImportHistory importHistory = ImportHistory.builder()
        				.importedAt(DateUtil.now())
        				.imported_by(currentUser)
        				.nupcoCode(e.getMessage())
        				.result("NOK")
        				.status("FAILURE")
        				.fileName(file.getOriginalFilename())
        				.message(list.toString())
        				.jobId(jobId)
        				.outlet(outlet)
        				.build();
        		importHistoryService.save(importHistory);
    		}
    		
    	}
    	);
    	return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, "The import has been finished, check the import history"))
                .body(null);
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
        result.setReceivedQty(result.getReceivedQty() + inventoryCommand.getCurrentBalance() );
        
        result.setCurrent_balance(result.getCurrent_balance() + inventoryCommand.getCurrentBalance());
        Double consumedQtyAdd = inventoryCommand.getConsumeQty() + inventoryCommand.getDamage() + inventoryCommand.getSample() + inventoryCommand.getWastage();
        result.setCurrent_balance(result.getCurrent_balance() - consumedQtyAdd);
        result.setConsumedQty(result.getConsumedQty() + consumedQtyAdd);
        result.setDamage(result.getDamage() + inventoryCommand.getDamage());
        result.setSample(result.getSample() + inventoryCommand.getSample());
        result.setWastage(result.getWastage() + inventoryCommand.getWastage());
        result.setReceivedUserQte(inventoryCommand.getCurrentBalance());
        result.setConsumedUserQte(inventoryCommand.getConsumeQty());
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
    
    /**
     * @param outlet
     * @return
     */
    @GetMapping("/inventories/badstock/{outlet}")
    public ResponseEntity<InventoryRepresentation> getInventoriesForBadStock(@PathVariable("outlet") String outlet) {
        log.debug("REST request to get Inventories for bad stock");
        List<String> statuses = new ArrayList<String>();
        statuses.add("oos");
        statuses.add("noos");
        
        Outlet outletExample = Outlet.builder().outletName(outlet).build();
        Optional<Outlet> optOutlet = outletService.findByExample(Example.of(outletExample));
        	
        List<Inventory> inventories = new ArrayList<>();
        InventoryRepresentation inventoryRepresentation = InventoryRepresentation.builder().build();
        if (outlet.equals("all")) {
        	inventories = inventoryService.findByStatusInAndIsLastInstance(statuses, Boolean.TRUE);
        	inventoryRepresentation = inventoryMapper.toInventoryRepresentation(inventories);
        }
        else if (optOutlet.isPresent()) {
        	inventories = inventoryService.findByStatusInAndIsLastInstanceAndOutlet(statuses, Boolean.TRUE, optOutlet.get());
        	inventoryRepresentation = inventoryMapper.toInventoryRepresentation(inventories);
        }
        return ResponseEntity.ok(inventoryRepresentation);
    }

    @GetMapping("/inventory/list")
    public ResponseEntity<InventoryRepresentation> getAllRepresentationInventories(InventoryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Inventories by criteria: {}", criteria);
        List<Inventory> inventories = inventoryQueryService.findByCriteria(criteria);
        inventories = inventories.stream()
                	.filter(inventory -> inventory.getIsLastInstance() != null ? inventory.getIsLastInstance().equals(Boolean.TRUE) : false) // 
                	.collect(Collectors.toList());
        
        //sort descending by lastUpdated
        inventories = inventories.stream().sorted(Comparator.comparing(Inventory::getLastUpdatedAt, Comparator.nullsLast(Comparator.reverseOrder()))).collect(Collectors.toList());
        InventoryRepresentation inventoryRepresentation = inventoryMapper.toInventoryRepresentation(inventories);
        return ResponseEntity.ok().body(inventoryRepresentation);
    }
    
    @GetMapping("/inventoryHistory/all")
    public ResponseEntity<InventoryRepresentation> getAllRepresentationInventoriesHistory() {
        log.debug("REST request to get Inventories by FindAll: {}");
        
        List<Inventory> inventories = inventoryService.findAll();
        InventoryRepresentation inventoryRepresentation = inventoryMapper.toInventoryRepresentation(inventories);
        return ResponseEntity.ok().body(inventoryRepresentation);
    }
    
    @PostMapping("/inventoryHistory/list")
    public ResponseEntity<InventoryRepresentation> getAllRepresentationInventoriesHistory(@RequestBody @Valid InventoryHistoryCommand inventoryHistoryCommand) {
        log.debug("REST request to get Inventories by FindByLastUpdatedAtBetween: {}");
        
        List<Inventory> inventories = inventoryService.findInventoryHistoryBetweenDate(inventoryHistoryCommand.getStartDate(), inventoryHistoryCommand.getEndDate());
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
