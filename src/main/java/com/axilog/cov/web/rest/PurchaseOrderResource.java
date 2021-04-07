package com.axilog.cov.web.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.context.Context;

import com.axilog.cov.domain.DynamicApprovalConfig;
import com.axilog.cov.domain.GrnHistSequence;
import com.axilog.cov.domain.GrnHistory;
import com.axilog.cov.domain.Inventory;
import com.axilog.cov.domain.Outlet;
import com.axilog.cov.domain.PoReport;
import com.axilog.cov.domain.PoStatus;
import com.axilog.cov.domain.Product;
import com.axilog.cov.domain.PurchaseOrder;
import com.axilog.cov.domain.Sequence;
import com.axilog.cov.domain.Substitute;
import com.axilog.cov.dto.command.POMailDetail;
import com.axilog.cov.dto.command.PurchaseOrderCommand;
import com.axilog.cov.dto.mapper.InventoryMapper;
import com.axilog.cov.dto.mapper.PurchaseOrderMapper;
import com.axilog.cov.dto.representation.GrnHistoryRepresentation;
import com.axilog.cov.dto.representation.InventoryPdfDetail;
import com.axilog.cov.dto.representation.PoApprovalRepresentation;
import com.axilog.cov.dto.representation.PoPdfDetail;
import com.axilog.cov.dto.representation.PoReportRepresentation;
import com.axilog.cov.dto.representation.PurchaseOrderRepresentation;
import com.axilog.cov.enums.PurchaseStatusEnum;
import com.axilog.cov.repository.GrnHistSequenceRepository;
import com.axilog.cov.repository.PoStatusRepository;
import com.axilog.cov.repository.SequenceRepository;
import com.axilog.cov.repository.SubstituteRepository;
import com.axilog.cov.security.SecurityUtils;
import com.axilog.cov.service.ApprovalService;
import com.axilog.cov.service.InventoryService;
import com.axilog.cov.service.OutletService;
import com.axilog.cov.service.PoMailService;
import com.axilog.cov.service.PoReportService;
import com.axilog.cov.service.ProductService;
import com.axilog.cov.service.PurchaseOrderHistoryService;
import com.axilog.cov.service.PurchaseOrderQueryService;
import com.axilog.cov.service.PurchaseOrderService;
import com.axilog.cov.service.dto.PurchaseOrderCriteria;
import com.axilog.cov.service.pdf.PdfService;
import com.axilog.cov.service.xlsx.XlsService;
import com.axilog.cov.util.DateUtil;
import com.axilog.cov.util.JsonUtils;
import com.axilog.cov.web.rest.errors.BadRequestAlertException;
import com.axilog.cov.web.rest.errors.NotFoundAlertException;
import com.lowagie.text.DocumentException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;

/**
 * REST controller for managing {@link com.axilog.cov.domain.PurchaseOrder}.
 */
@SuppressWarnings("unused")
@RestController
@RequestMapping("/api")
@Api(tags = "Purchase Management", value = "PurchaseManagement", description = "Controller for Purchase Management")
public class PurchaseOrderResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderResource.class);

    private static final String ENTITY_NAME = "purchaseOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PurchaseOrderService purchaseOrderService;

    @Autowired
    private PurchaseOrderHistoryService purchaseOrderHistoryService;
    
    private final PurchaseOrderQueryService purchaseOrderQueryService;

    @Autowired
    private InventoryService inventoryService;
    
    @Autowired
    private PoMailService poMail;
    
    @Autowired
    private PdfService pdfService;
    
    @Autowired
    private XlsService xlsService;
    
    @Autowired
    private InventoryMapper inventoryMapper;
    
    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;
    
    @Autowired
    private PoStatusRepository poStatusRepository;
    
    @Autowired
    private SequenceRepository sequenceRepository;
    
    @Autowired
    private GrnHistSequenceRepository grnHistSequenceRepository;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private OutletService outletService;
    
    @Autowired
    private PoReportService poReportService;
    
    @Autowired
    private SubstituteRepository substituteRepository;
    
    @Value("${poEmailReceiver}")
    private String[] poEmailReceiver;
    
    @Value("${poSubjectEmail}")
    private String poSubjectEmail;
    
    @Value("${poEmailApprovalLevel1}")
    private String[] poEmailApprovalLevel1;
    
    @Value("${poEmailApprovalLevel2}")
    private String[] poEmailApprovalLevel2;
    
    @Value("${poThreesholdCapacity}")
	private Double poThreesholdCapacity;
    
    @Autowired
    private ApprovalService approvalService;
    
    private String message;
    boolean atLeastOutletFound = false;
    
    public PurchaseOrderResource(PurchaseOrderService purchaseOrderService, PurchaseOrderQueryService purchaseOrderQueryService) {
        this.purchaseOrderService = purchaseOrderService;
        this.purchaseOrderQueryService = purchaseOrderQueryService;
    }

    /**
     * {@code POST  /purchase-orders} : Create a new purchaseOrder.
     *
     * @param purchaseOrder the purchaseOrder to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new purchaseOrder, or with status {@code 400 (Bad Request)} if the purchaseOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/purchase-orders")
    public ResponseEntity<PurchaseOrder> createPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) throws URISyntaxException {
        log.debug("REST request to save PurchaseOrder : {}", purchaseOrder);
        if (purchaseOrder.getId() != null) {
            throw new BadRequestAlertException("A new purchaseOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurchaseOrder result = purchaseOrderService.save(purchaseOrder);
        return ResponseEntity.created(new URI("/api/purchase-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/purchaseOrders/update")
    public void updateStatusPurchaseOrder(@RequestBody PurchaseOrderCommand purchaseOrderCommand) throws URISyntaxException {
        log.debug("REST request to save PurchaseOrder : {}", purchaseOrderCommand);
        if (purchaseOrderCommand == null) {
            throw new BadRequestAlertException("null body", ENTITY_NAME, "idexists");
        }
        PurchaseOrder result = purchaseOrderService.findByOrderNo(purchaseOrderCommand.getOrderNo());
        if (result == null) {
        	throw new BadRequestAlertException("Purchase Order does not exist with Order NO", ENTITY_NAME, "OrderNo does not exist");
        }
        List<PoStatus> poStatusExisting = result.getPoStatuses().stream().filter(poSt -> poSt.getStatus().equals(purchaseOrderCommand.getStatus())).collect(Collectors.toList());
        if (poStatusExisting == null || poStatusExisting.isEmpty()) {
            PoStatus poStatus = PoStatus.builder().status(purchaseOrderCommand.getStatus()).updatedAt(DateUtil.now()).build();
            poStatus = poStatusRepository.save(poStatus);
            result.getPoStatuses().add(poStatus);
            result.setUpdatedAt(DateUtil.now());
            result = purchaseOrderService.save(result);
        }

    }
    /**
     * {@code PUT  /purchase-orders} : Updates an existing purchaseOrder.
     *
     * @param purchaseOrder the purchaseOrder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated purchaseOrder,
     * or with status {@code 400 (Bad Request)} if the purchaseOrder is not valid,
     * or with status {@code 500 (Internal Server Error)} if the purchaseOrder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/purchase-orders")
    public ResponseEntity<PurchaseOrder> updatePurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) throws URISyntaxException {
        log.debug("REST request to update PurchaseOrder : {}", purchaseOrder);
        if (purchaseOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PurchaseOrder result = purchaseOrderService.save(purchaseOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, purchaseOrder.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /purchase-orders} : get all the purchaseOrders.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of purchaseOrders in body.
     */
    @GetMapping("/purchase-orders")
    public ResponseEntity<List<PurchaseOrder>> getAllPurchaseOrders(PurchaseOrderCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PurchaseOrders by criteria: {}", criteria);
        Page<PurchaseOrder> page = purchaseOrderQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * @param criteria
     * @param pageable
     * @return
     */
    @GetMapping("/purchaseOrders/list")
    public ResponseEntity<PurchaseOrderRepresentation> getPurchaseOrders(PurchaseOrderCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PurchaseOrders by criteria: {}", criteria);
        List<PurchaseOrder> poList = purchaseOrderService.findAll();
        PurchaseOrderRepresentation purchaseOrderRepresentation = purchaseOrderMapper.toPurchaseRepresentation(poList);
        return ResponseEntity.ok().body(purchaseOrderRepresentation);
    }
    
    /**
     * @param criteria
     * @param pageable
     * @return
     */
    @GetMapping("/approval/config/{currentStatus}/{outlet}")
    public ResponseEntity<DynamicApprovalConfig> getNextStatus(@PathVariable("currentStatus") String currentStatus, @PathVariable("outlet") String outlet) {
        log.debug("REST request to get PurchaseOrders by currentStatus: {} and outlet: {}", currentStatus, outlet);
        DynamicApprovalConfig approvalConfig = approvalService.findbyCurrentStatusandOutlet(currentStatus, outlet);
        if (approvalConfig == null) {
        	throw new BadRequestAlertException("notFound", ENTITY_NAME, "Approval Config Data not valid, please check the config table");
        }
        return ResponseEntity.ok().body(approvalConfig);
    }
    /**
     * {@code GET  /purchase-orders/count} : count all the purchaseOrders.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/purchase-orders/count")
    public ResponseEntity<Long> countPurchaseOrders(PurchaseOrderCriteria criteria) {
        log.debug("REST request to count PurchaseOrders by criteria: {}", criteria);
        return ResponseEntity.ok().body(purchaseOrderQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /purchase-orders/:id} : get the "id" purchaseOrder.
     *
     * @param id the id of the purchaseOrder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the purchaseOrder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/purchase-orders/{id}")
    public ResponseEntity<PurchaseOrder> getPurchaseOrder(@PathVariable Long id) {
        log.debug("REST request to get PurchaseOrder : {}", id);
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(purchaseOrder);
    }

    /**
     * {@code GET  /purchase-orders/:id} : get the "id" purchaseOrder.
     *
     * @param id the id of the purchaseOrder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the purchaseOrder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/purchase-orders/orderNo/{orderNo}")
    public ResponseEntity<PurchaseOrder> getPurchaseOrderByPoNumber(@PathVariable Long orderNo) {
        log.debug("REST request to get PurchaseOrder : {}", orderNo);
        Example<PurchaseOrder> examplePurchaseOrder = Example.of(PurchaseOrder.builder().orderNo(orderNo).build());
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderService.findOne(examplePurchaseOrder);
        return ResponseUtil.wrapOrNotFound(purchaseOrder);
    }
    
    /**
     * {@code GET  /purchase-orders/:id} : get the "id" purchaseOrder.
     *
     * @param id the id of the purchaseOrder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the purchaseOrder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/purchase-orders/sent/{orderNo}")
    public ResponseEntity<PurchaseOrder> getSentPurchaseOrderByPoNumber(@PathVariable Long orderNo) {
        log.debug("REST request to get PurchaseOrder : {}", orderNo);
        Set<PoStatus> poStatuses = new HashSet<>();
        poStatuses.add(PoStatus.builder().status("SENT_TO_NUPCO").build());
        
        Example<PurchaseOrder> examplePurchaseOrder = Example.of(PurchaseOrder.builder().orderNo(orderNo).build());
        
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderService.findOne(examplePurchaseOrder);
        if (purchaseOrder.isPresent()) {
        	List<PoStatus> listStatus = purchaseOrder.get().getPoStatuses().stream().filter(status -> status.getStatus().equals("SENT_TO_NUPCO")).collect(Collectors.toList());
        	if (!listStatus.isEmpty()) {
        		return ResponseUtil.wrapOrNotFound(purchaseOrder);
        	}
        }
        throw new NotFoundAlertException("notFound", ENTITY_NAME, "No Po has ben sent to nupco with this Number");
    }

    /**
     * {@code DELETE  /purchase-orders/:id} : delete the "id" purchaseOrder.
     *
     * @param id the id of the purchaseOrder to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/purchase-orders/{id}")
    public ResponseEntity<Void> deletePurchaseOrder(@PathVariable Long id) {
        log.debug("REST request to delete PurchaseOrder : {}", id);
        purchaseOrderService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    
    @GetMapping("/purchase-orders/autoreplenish")
    public ResponseEntity<PoApprovalRepresentation> autoReplenishOnDemand() throws IllegalArgumentException, IllegalAccessException, IOException, DocumentException {
        return taskForAutoReplunish();
    }

    @Scheduled(cron = "${autoReplenishCron}")
    public ResponseEntity<PoApprovalRepresentation> taskForAutoReplunish() throws IOException, DocumentException, IllegalArgumentException, IllegalAccessException {
    	
    	DynamicApprovalConfig approvalConfig = approvalService.findStartStatus();
    	if (!isValidApprovalConfig(approvalConfig)) {
    		throw new BadRequestAlertException("notFound", ENTITY_NAME, "Approval Config Data not valid, please check the config table");
    	}
    	
        long now = System.currentTimeMillis() / 1000;
        log.info("schedule tasks For Auto Replunish using cron jobs - " + now);
        List<Inventory> inventories = getProductToBeOrdered();
        List<PurchaseOrder> orders = purchaseOrderService.findAll();
        List<Outlet> outlets = outletService.findAll();
        
        
        outlets.forEach(outlet -> {
        	log.info("Processing outlet {} for replenish now action", outlet.getOutletName());
		try {
			List<Product> productsHavePo = new ArrayList<>();
	        if (orders != null) {
	        	productsHavePo.clear();
	        	orders.forEach(order -> {
	        		List<PoStatus> foundStatus = order.getPoStatuses().stream().filter(poStatus -> poStatus.getStatus().equals("IN_VALIDATION")).collect(Collectors.toList());
	        		if (foundStatus != null && !foundStatus.isEmpty() && order.getOutlet()!=null && order.getOutlet().equals(outlet)) {
	        			productsHavePo.addAll(order.getProducts());
	        		}
	        		
	        	});
	        	
	        }
			 Sequence currSeq = sequenceRepository.curVal();
             Long currVal = currSeq.getCurrentNumber();
             currVal = currVal + 1;
             currSeq.setCurrentNumber(currVal);
             sequenceRepository.save(currSeq);
             
			List<Product> productsToBeInPo = new ArrayList<>();
			List<Product> productsToBeInPoWithDiffBalance = new ArrayList<>();
			
			if (inventories != null) {
	        	productsToBeInPo = inventories.stream().map(Inventory ::  getProduct).collect(Collectors.toList());
	        	productsToBeInPoWithDiffBalance = inventories.stream().filter(inv -> productsHavePo.contains(inv.getProduct())).filter(inv -> inv.getCurrent_balance() < getLastPreviousBalance(inv.getProduct().getProductCode(), outlet.getOutletName())).map(Inventory ::  getProduct).collect(Collectors.toList());
	        	productsToBeInPo.addAll(productsToBeInPoWithDiffBalance);
			}
			List<Inventory> inventoriesPerOutlet = inventories.stream().filter(inventory -> inventory.getOutlet().equals(outlet)).collect(Collectors.toList());
    		PoPdfDetail detail = inventoryMapper.toPdfListDetail(inventoriesPerOutlet, productsToBeInPo, outlet, currVal, productsHavePo, orders);
            if (detail == null || detail.getListDetails() == null || detail.getListDetails().isEmpty()) {
            	log.info("No Po to be ordered for outlet: {}", outlet.getOutletName());
            	message = "No Po to be order";
            }
            else {
            	atLeastOutletFound = true;
            	File poPdf = pdfService.generatePdf(detail);
            	File poXlsx = xlsService.exportExcel(detail);
        		byte[] fileContent = FileUtils.readFileToByteArray(poPdf);
        		byte[] fileContentXlsx = FileUtils.readFileToByteArray(poXlsx);
                List<Product> products = new ArrayList<>();
                if (inventories != null) {
                	products = inventories.stream().filter(inv -> inv.getOutlet().equals(outlet)).map(Inventory:: getProduct).collect(Collectors.toList());
                }
                String login = "NA";
                if (SecurityUtils.getCurrentUserLogin().isPresent()) {
                	login = SecurityUtils.getCurrentUserLogin().get();
                }
                Set<PoStatus> poStatusList = new HashSet<>();
                
                PoStatus poCreatedStatus = PoStatus.builder().status(PurchaseStatusEnum.CREATED.getLabel()).updatedAt(DateUtil.now()).build();
                poCreatedStatus = poStatusRepository.save(poCreatedStatus);
                poStatusList.add(poCreatedStatus);
                
                Date nowDate = DateUtil.addSeconds(DateUtil.now(), 2);
                
                PoStatus poPending1Status = PoStatus.builder().status(approvalConfig.getCurrentStepStatus()).updatedAt(nowDate).build();
                poPending1Status = poStatusRepository.save(poPending1Status);
                poStatusList.add(poPending1Status);
               
                SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.MOI_DATE_TIME_ENCODING);
                PurchaseOrder po = PurchaseOrder.builder()
        				.products(products)
        				.createdAt(DateUtil.now())
        				.createdBy(login)
        				.deliveredDate(sdf.parse(detail.getHeaderPdfDetail().getDueDate()))
        				.poStatuses(poStatusList)
        				.orderNo(currVal)
        				.data(fileContent)
        				.dataXlsx(fileContentXlsx)
        				.outlet(outlet)
        				.hotJson(JsonUtils.toJsonString(detail))
        				.build();
        		PurchaseOrder result = purchaseOrderService.save(po);
        		POMailDetail poMailDetail = POMailDetail.builder()
        				.poNumber(currVal)
        				.status(approvalConfig.getNextStepStatus())
        				.approvalLevel(1)
        				.emailToBeSent(true)
        				.build();
        		Context context = pdfService.getContext(poMailDetail, "mailDetail");
        		String html = pdfService.loadAndFillTemplate(context, "mail/poValidationEmail");
        		String[] recipients = new String[0];
        		if (approvalConfig.getCurrentStepEmail() != null) {
        			recipients = approvalConfig.getCurrentStepEmail().split(",");
        		}
        		String[] cc = new String[0];
        		if (approvalConfig.getCurrentStepEmailcc() != null) {
        			cc = approvalConfig.getCurrentStepEmailcc().split(",");
        		}
        		poMail.sendEmailWithAttachmentAndMultiple(recipients, cc, poSubjectEmail + " ==> "+ outlet.getOutletName(), html, true, true, poPdf, poXlsx);
        		message = "generated PO with the following details: number: "+currVal;
            }
			
		}
		catch(Exception e) {
			log.error("Exception when looping over inventory to create their Po", e);
			throw new BadRequestAlertException("Internal Error", ENTITY_NAME, e.getMessage());
		}
		
	});
        PoApprovalRepresentation po = PoApprovalRepresentation.builder()
    			.message(message)
    			.build();
        if (atLeastOutletFound) {
        	atLeastOutletFound = false;
        	return ResponseEntity.ok(po);	
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(po);
        
    }
    @PostMapping(value = "/purchaseOrders/edit", consumes = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<PurchaseOrder> updatePurchaseOrder1(@RequestBody PoPdfDetail detail) throws URISyntaxException, IOException, DocumentException {
        log.debug("REST request to update PurchaseOrder : {}", detail);
        File poPdf = pdfService.generatePdf(detail);
		byte[] fileContent = FileUtils.readFileToByteArray(poPdf);
        if (detail == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PurchaseOrder persistedPo = purchaseOrderService.findByOrderNo(detail.getHeaderPdfDetail().getOrderNumber());
        // update po
        persistedPo.setData(fileContent);
        persistedPo.setHotJson(JsonUtils.toJsonString(detail));
        PurchaseOrder result = purchaseOrderService.save(persistedPo);
        
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, Long.toString(result.getOrderNo())))
            .body(result);
    }
    
    
    @PostMapping(value = "/purchaseOrders/acquire", consumes = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<PurchaseOrder> acquirePurchaseOrder(@RequestBody PoPdfDetail detail) throws URISyntaxException, IOException, DocumentException {
        log.debug("REST request to update PurchaseOrder : {}", detail);
        File poPdf = pdfService.generatePdf(detail);
		byte[] fileContent = FileUtils.readFileToByteArray(poPdf);
        if (detail == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PurchaseOrder oldPo = purchaseOrderService.findByOrderNo(detail.getHeaderPdfDetail().getOrderNumber());
        PurchaseOrder persistedPo = purchaseOrderService.findByOrderNo(detail.getHeaderPdfDetail().getOrderNumber());
        
        PoPdfDetail originalPdfDetail = JsonUtils.toJsonObject(persistedPo.getHotJson());
         
        // update po
        persistedPo.setData(fileContent);
        persistedPo.setHotJson(JsonUtils.toJsonString(detail));
        // check if we have updated one product with its substitute
        
        List<String> newAcquiredItems = detail.getListChangedDetails().stream().map(dt -> dt.getCode()).collect(Collectors.toList());
        List<String> existingAcquiredItems = persistedPo.getProducts().stream().map(product -> product.getProductCode()).collect(Collectors.toList());
        
        List<Product> remainingProductInPo = persistedPo.getProducts().stream().filter(product -> newAcquiredItems.contains(product.getProductCode())).collect(Collectors.toList());
        List<InventoryPdfDetail> addedProductInPo = detail.getListChangedDetails().stream().filter(dt -> !existingAcquiredItems.contains(dt.getCode())).collect(Collectors.toList());
        
        List<PoStatus> poStatusExisting = persistedPo.getPoStatuses().stream().filter(poSt -> poSt.getStatus().equals("GRN_PARTIAL")).collect(Collectors.toList());
        String grnStatus = "";
        if (poStatusExisting.isEmpty()) {
        	PoStatus poStatus = PoStatus.builder().status("GRN_PARTIAL").updatedAt(DateUtil.now()).build();
        	poStatus = poStatusRepository.save(poStatus);
        	persistedPo.getPoStatuses().add(poStatus);
        	grnStatus = "GRN_PARTIAL";
        }
        else {
        	List<InventoryPdfDetail> listDetails = detail.getListDetails().stream().filter(rowDetail -> !rowDetail.getQuantity().equals(rowDetail.getReceivedQuantity())).collect(Collectors.toList());
        	if (listDetails.isEmpty()) {
        		PoStatus poStatus = PoStatus.builder().status("GRN_COMPLETED").updatedAt(DateUtil.now()).build();
            	poStatus = poStatusRepository.save(poStatus);
        		persistedPo.getPoStatuses().add(poStatus);
        		
        		PoStatus poStatusClosed = PoStatus.builder().status("CLOSED").updatedAt(DateUtil.addMinutes(DateUtil.now(), 2)).build();
            	poStatus = poStatusRepository.save(poStatusClosed);
        		persistedPo.getPoStatuses().add(poStatus);
        		grnStatus = "CLOSED";
        	}
        	else {
        		grnStatus = "GRN_PARTIAL";
        	}
        }
        PurchaseOrder result = purchaseOrderService.save(persistedPo);
        
	     // add grn history
	   	 String json = result.getHotJson();
	     final Outlet outlet = result.getOutlet();
	     final String grnLastStatus = grnStatus;
	   	 
	     GrnHistSequence currentSeq = grnHistSequenceRepository.curVal();
	     Long nextVal = currentSeq.getCurrentNumber() + 1;
		 currentSeq.setCurrentNumber(nextVal);
		 grnHistSequenceRepository.save(currentSeq);
			
		 // product processing
		 remainingProductInPo.forEach(product -> {
	     		String login = "NA";
	           if (SecurityUtils.getCurrentUserLogin().isPresent()) {
	           	login = SecurityUtils.getCurrentUserLogin().get();
	           }
	     PoPdfDetail pdfDetailNew;
		try {
			pdfDetailNew = JsonUtils.toJsonObject(json);
			Optional<InventoryPdfDetail> invOpt = pdfDetailNew.getListChangedDetails().stream().filter(detailPo -> detailPo.getCode().equals(product.getProductCode())).findFirst();
		   	if (invOpt.isPresent()) {
		   		if (invOpt.get().getSubsCodesCol() != null && !invOpt.get().getSubsCodesCol().isEmpty()) {
		   			Substitute substituteData = substituteRepository.findBySubstituteCode(invOpt.get().getSubsCodesCol());
		        	if (substituteData !=  null) {
		        		Double impactFactor = substituteData.getImpactFactor();
			   			createNewGrn(product, invOpt.get().getSubsCodesCol(), outlet, json, originalPdfDetail, grnLastStatus, login, nextVal, impactFactor, true, oldPo);
		        	}
		   		}
		   		else {
		   			createNewGrn(product, product.getProductCode(), outlet, json, originalPdfDetail, grnLastStatus, login, nextVal, 1D, false, oldPo);
		   		}
		   	}
			
		   	 
		   	 
		   	 
		   	 
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	     
	   	});
		 
		 // substitute processing
		 
		 addedProductInPo.forEach(newProduct -> {
	     		String login = "NA";
	           if (SecurityUtils.getCurrentUserLogin().isPresent()) {
	           	login = SecurityUtils.getCurrentUserLogin().get();
	           }
	           
	           if (newProduct.getSubsCodesCol() != null && !newProduct.getSubsCodesCol().isEmpty()) {
	        	   Substitute substituteData = substituteRepository.findBySubstituteCode(newProduct.getSubsCodesCol());
	        	   Double impactFactor = substituteData.getImpactFactor();
	        	   Product product = substituteData.getProduct();
	        	   createNewGrn(product, newProduct.getSubsCodesCol(), outlet, json, originalPdfDetail, grnLastStatus, login, nextVal, impactFactor, true, oldPo);
	           }
	           else {
	        	 
	        	   List<Product> products = productService.findByProductCode(newProduct.getCode());
	        	   if (products != null && products.size() > 0) {
	        		   createNewGrn(products.get(0), newProduct.getCode(), outlet, json, originalPdfDetail, grnLastStatus, login, nextVal, 1D, false, oldPo);
	        	   }
	        	   
	           }
	   		
	   	});
        // check received qte
        detail.getListDetails().forEach(rowDetail -> {
        	Optional<Product> productOpt = productService.findOne(Example.of(Product.builder().productCode(rowDetail.getCode()).build()));
        	if (productOpt.isPresent()) {
        		List<Inventory> invOpt = inventoryService.findByOutletAndProductAndIsLastInstance(result.getOutlet(), productOpt.get(), Boolean.TRUE);
            	if (invOpt != null && !invOpt.isEmpty()) {
            		Inventory inventory = invOpt.get(0);
            		inventory.setIsLastInstance(Boolean.FALSE);
            		inventory = inventoryService.save(inventory);
                    
                    //create new entry with new date
            		inventory.setReceivedQty(rowDetail.getReceivedQuantity());
            		inventory.setQuantitiesInTransit(rowDetail.getQuantity() - rowDetail.getReceivedQuantity());
        			inventory.setCurrent_balance(inventory.getCurrent_balance() + rowDetail.getReceivedQuantity());
                    
        			inventory.setLastUpdatedAt(DateUtil.now());
        			inventory.setId(null);
                    inventory.setIsLastInstance(Boolean.TRUE);
                    inventory = inventoryService.save(inventory);
                    
        			
            	}
        	}
        	
        });
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, Long.toString(result.getOrderNo())))
            .body(result);
    }
    
    private void createNewGrn(Product product, String code, Outlet outlet, String json, PoPdfDetail originalPdfDetail, String grnLastStatus, String login, Long grnNumber, Double impactFactor, boolean isSubstitute, PurchaseOrder oldPo) {
    	if (product != null) {
 		   List<Inventory> inventories = inventoryService.findByOutletAndProductAndIsLastInstance(outlet, product, Boolean.TRUE);
   	       	if (inventories != null && !inventories.isEmpty()) {
   	       		Inventory inventory = inventories.get(0);
   	       		PoPdfDetail pdfDetail;
   					try {
   						pdfDetail = JsonUtils.toJsonObject(json);
   						Optional<InventoryPdfDetail> invOpt = Optional.empty();
   						if (isSubstitute) {
   							invOpt = pdfDetail.getListChangedDetails().stream().filter(detailPo -> detailPo.getSubsCodesCol().equals(code)).findFirst();
   						}
   						else {
   							invOpt = pdfDetail.getListChangedDetails().stream().filter(detailPo -> detailPo.getCode().equals(code)).findFirst();
   						}
   						
   		           		if (invOpt.isPresent()) {
   		           			//check if it substitute or not
   		           			Double receivedQty = Math.floor(invOpt.get().getReceivedQuantity());
   		           			GrnHistory grnHistory = GrnHistory.builder()
   		           					.grnNumber(Long.toString(grnNumber))
   		           	    			.createdAt(DateUtil.now())
   		           	    			.createdBy(login)
   		           	    			.productCode(inventory.getProduct().getProductCode())
   		           	    			.category(invOpt.get().getCategory())
   		           	    			.description(invOpt.get().getDescription())
   		           	    			.poQuantity(invOpt.get().getQuantity())
   		           	    			.received(receivedQty)
   		           	    			.uom(invOpt.get().getUom())
   		           	    			.status(grnLastStatus)
   		           	    			.orderNo(pdfDetail.getHeaderPdfDetail().getOrderNumber())
   		           	    			.outletName(outlet.getOutletName())
	   		           	    		.subsCode(invOpt.get().getSubsCodesCol())
		           	    			.subsCategory(invOpt.get().getSubsCategory())
		           	    			.subsDescription(invOpt.get().getSubsDescription())
   		           	    			.build();
   		           			purchaseOrderService.saveGrn(grnHistory);
   		           			
   		           			inventory.setIsLastInstance(Boolean.FALSE);
   		            		inventory = inventoryService.save(inventory);
   		            		
   		            		inventory.setReceivedUserQte(receivedQty);
   		           			inventory.setIsLastInstance(Boolean.TRUE);
   		           			inventory.setId(null);
   		           			inventory.setLastUpdatedAt(DateUtil.now());
   		            		final Inventory inventorySaved = inventoryService.save(inventory);
   		            		
   		           			// save new instance for poReport
   		           			Double originalQty = originalPdfDetail.getListDetails().stream().filter(dt -> dt.getCode().equals(inventorySaved.getProduct().getProductCode())).map(dt -> dt.getQuantity()).findFirst().orElse(0d);
   		           			PoReport poReport = PoReport.builder()
   		           					.description(inventory.getProduct().getDescription())
   		           					.etaOfDelivery(100L)
   		           					.item(inventory.getProduct().getProductCode())
   		           					.outlet(outlet.getOutletName())
   		           					.poBalanceQty(invOpt.get().getQuantity())
   		           					.poOriginalQty(originalQty)
   		           					.poReceivedQty(receivedQty)
   		           					.uom(inventory.getUom())
   		           					.build();
   		           			poReportService.save(poReport);
   		           			
   		           		 // save a new history
   		                 purchaseOrderHistoryService.save(purchaseOrderMapper.toHistory(oldPo));
   		           		}
   						} catch (JsonProcessingException e) {
   							e.printStackTrace();
   						}
   	       		
   	       		
   	       	}
    	}
    }
    @PostMapping("/purchaseOrders/approval")
    @Transactional
    public ResponseEntity<PoApprovalRepresentation> approvePurchaseOrder(@RequestBody PurchaseOrderCommand purchaseOrderCommand) throws URISyntaxException, IOException, IllegalArgumentException, IllegalAccessException {
        log.debug("REST request to save PurchaseOrder : {}", purchaseOrderCommand);
        if (purchaseOrderCommand == null) {
        	throw new BadRequestAlertException("date input are null", ENTITY_NAME, "null");
        }
        PurchaseOrder result = purchaseOrderService.findByOrderNo(purchaseOrderCommand.getOrderNo());
        if (result == null) {
        	throw new BadRequestAlertException("notFound", ENTITY_NAME, "Cannot find an Order whith the input data");
        }
        log.info("get next status from config table for po {}, current status is: {}", purchaseOrderCommand.getOrderNo(), purchaseOrderCommand.getStatus());
        DynamicApprovalConfig approvalConfig = approvalService.findbyCurrentStatusandOutlet(purchaseOrderCommand.getStatus(), result.getOutlet().getOutletName());
    	if (!isValidApprovalConfig(approvalConfig)) {
    		throw new BadRequestAlertException("Approval Response", ENTITY_NAME, "Approval Config Data not valid, please check the config table");
    	}
    	
    	 String json = result.getHotJson();
         final Outlet outlet = result.getOutlet();
         String login = "NA";
         if (SecurityUtils.getCurrentUserLogin().isPresent()) {
         	login = SecurityUtils.getCurrentUserLogin().get();
         }
    	List<PoStatus> poStatusExisting = result.getPoStatuses().stream().filter(poSt -> poSt.getStatus().equals(purchaseOrderCommand.getStatus())).collect(Collectors.toList());
        if (poStatusExisting == null || poStatusExisting.isEmpty()) {
        	PoStatus poStatus = PoStatus.builder().status(purchaseOrderCommand.getStatus()).updatedAt(DateUtil.now()).build();
            poStatus = poStatusRepository.save(poStatus);
            result.getPoStatuses().add(poStatus);
            result.setUpdatedAt(DateUtil.now());
            result.setApprovalOwner(login);
            result.setApprovalReceivingTime(result.getApprovalTime());
            result.setApprovalTime(DateUtil.now());
            result = purchaseOrderService.save(result);
            
           
            if (purchaseOrderCommand.getStatus().equals(PurchaseStatusEnum.SENT_TO_NUPCO.getLabel())) {
            	result.getProducts().forEach(product -> {
            		List<Inventory> inventories = inventoryService.findByOutletAndProductAndIsLastInstance(outlet, product, Boolean.TRUE);
                	if (inventories != null && !inventories.isEmpty()) {
                		Inventory inventory = inventories.get(0);
                		PoPdfDetail pdfDetail;
						try {
							pdfDetail = JsonUtils.toJsonObject(json);
							Optional<InventoryPdfDetail> invOpt = pdfDetail.getListDetails().stream().filter(detail -> detail.getCode().equals(product.getProductCode())).findFirst();
	                		if (invOpt.isPresent()) {
	                			inventory.setQuantitiesInTransit(invOpt.get().getQuantity());	
	                			inventoryService.save(inventory);
	                		}
						} catch (JsonProcessingException e) {
							e.printStackTrace();
						}
                		
                		
                	}
            	});
            }
            
            
           
            
            POMailDetail poMailDetail = POMailDetail.builder()
    				.poNumber(result.getOrderNo())
    				.status(approvalConfig.getNextStepStatus())
    				.approvalLevel(2)
    				.emailToBeSent(true)
    				.build();
			Context context = pdfService.getContext(poMailDetail, "mailDetail");
			String html = "";
			if (approvalConfig.getFinalStatus().equals(Boolean.TRUE)) {
				html = pdfService.loadAndFillTemplate(context, "mail/poEmail");
			}
			else {
				html = pdfService.loadAndFillTemplate(context, "mail/poApprovalEmail");
			}
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-MM-SS");
    		File tempFile = File.createTempFile("order", sdf.format(DateUtil.now()));
    		FileOutputStream fos = new FileOutputStream(tempFile);
    		fos.write(result.getData());
    		fos.close();
    		
    		File tempXlsFile = File.createTempFile("order", sdf.format(DateUtil.now()));
    		FileOutputStream xlsfos = new FileOutputStream(tempXlsFile);
    		if (xlsfos != null && result != null && result.getDataXlsx() != null) {
    			xlsfos.write(result.getDataXlsx());
    			xlsfos.close();
    		}
    		
    		String[] recipients = new String[0];
    		if (approvalConfig.getCurrentStepEmail() != null) {
    			recipients = approvalConfig.getCurrentStepEmail().split(",");
    		}
    		String[] cc = new String[0];
    		if (approvalConfig.getCurrentStepEmailcc() != null) {
    			cc = approvalConfig.getCurrentStepEmailcc().split(",");
    		}
    		poMail.sendEmailWithAttachmentAndMultiple(recipients, cc, poSubjectEmail + " ==> "+approvalConfig.getCurrentStepStatus(), html, true, true, tempFile, tempXlsFile);	
    		return ResponseEntity.ok(PoApprovalRepresentation.builder().message("Success Po Generation").build());
        }
    	return ResponseEntity.ok(PoApprovalRepresentation.builder().message("No Action Has been done").build());
    }
    
    
    @GetMapping("/poInventory")
    public ModelAndView studentsView(ModelAndView modelAndView) {
    	List<String> status = new ArrayList<String>();
        status.add("in");
        status.add("noos");
        modelAndView.addObject("inventories", inventoryService.findByStatusIn(status));
        modelAndView.setViewName("inventories");
        return modelAndView;
    }

    @GetMapping("/downloadPo")
    public void downloadPDFResource(HttpServletResponse response) {
        try {
            Path file = null;//Paths.get(pdfService.generatePdf(inventoryMapper.toPdfListDetail(getProductToBeOrdered())).getAbsolutePath());
            if (Files.exists(file)) {
                response.setContentType("application/pdf");
                response.addHeader("Content-Disposition",
                        "attachment; filename=" + file.getFileName());
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private List<Inventory> getProductToBeOrdered() {
    	 List<String> status = new ArrayList<String>();
    	 status.add("in");
         status.add("oos");
         status.add("noos");
         return inventoryService.findByStatusInAndIsLastInstanceAndCapacityLessThan(status, Boolean.TRUE, poThreesholdCapacity);
    }
    
    private boolean isValidApprovalConfig(DynamicApprovalConfig approvalConfig) throws IllegalArgumentException, IllegalAccessException {
    	if (approvalConfig == null) return false; 
    	if (approvalConfig.getFinalStatus() == null ||
    			 approvalConfig.getStartStatus() == null ||
    			 approvalConfig.getCurrentStep() == null || approvalConfig.getCurrentStep().isEmpty() ||
    			 approvalConfig.getCurrentStepEmail() == null || approvalConfig.getCurrentStepEmail().isEmpty() ||
    			 approvalConfig.getCurrentStepStatus() == null || approvalConfig.getCurrentStepStatus().isEmpty() ||
    			 approvalConfig.getNextStep() == null || approvalConfig.getNextStep().isEmpty() ||
    			 approvalConfig.getNextStepStatus() == null || approvalConfig.getNextStepStatus().isEmpty() ||
    			 approvalConfig.getOutlet() == null || approvalConfig.getOutlet().isEmpty()
    			 )
         return false;   
         return true;        
    }
    
    @GetMapping("/export/excel/{orderNo}")
    public byte[] exportToExcel(HttpServletResponse response, @PathVariable("orderNo") Long orderNo) throws IOException {
    	response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=deployment-definitions.xlsx");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        
        Example<PurchaseOrder> examplePurchaseOrder = Example.of(PurchaseOrder.builder().orderNo(orderNo).build());
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderService.findOne(examplePurchaseOrder);
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=orders_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        if (purchaseOrder.isPresent())
        	return purchaseOrder.get().getDataXlsx();
        return null;
    }  
    
    @GetMapping("/purchaseOrders/grnHistory")
    public ResponseEntity<GrnHistoryRepresentation> getAllGrnHistory() {
        log.debug("REST request to get All GrnHistory");
        List<GrnHistory> grnHistories = purchaseOrderService.findAllGrn();
        GrnHistoryRepresentation grnHistoryRepresentation = purchaseOrderMapper.toGrnHistoryRepresentation(grnHistories);
        return ResponseEntity.ok().body(grnHistoryRepresentation);
    }
   
    @GetMapping("/purchaseOrders/substitute/all")
    public ResponseEntity<List<Substitute>> getAllSubstitutes() {
        log.debug("REST request to get All Substitute");
        List<Substitute> substitutes = substituteRepository.findAll();
        return ResponseEntity.ok().body(substitutes);
    }
    @GetMapping("/purchaseOrders/poReport")
    public ResponseEntity<PoReportRepresentation> getAllPoRepport() {
        log.debug("REST request to get All PoReport");
        List<PoReport> poReports = purchaseOrderService.findAllPoReport();
        PoReportRepresentation poReportRepresentation = purchaseOrderMapper.toPoReportRepresentation(poReports);
        return ResponseEntity.ok().body(poReportRepresentation);
    }
    
    private Double getLastPreviousBalance(String productCode, String outlet) {
    	return inventoryService.getPreviousCurrentBallence(productCode, outlet);
    }
}
