package com.axilog.cov.web.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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

import com.axilog.cov.domain.Inventory;
import com.axilog.cov.domain.Outlet;
import com.axilog.cov.domain.PoStatus;
import com.axilog.cov.domain.Product;
import com.axilog.cov.domain.PurchaseOrder;
import com.axilog.cov.domain.Sequence;
import com.axilog.cov.dto.command.POMailDetail;
import com.axilog.cov.dto.command.PurchaseOrderCommand;
import com.axilog.cov.dto.mapper.InventoryMapper;
import com.axilog.cov.dto.mapper.PurchaseOrderMapper;
import com.axilog.cov.dto.representation.PoPdfDetail;
import com.axilog.cov.dto.representation.PurchaseOrderRepresentation;
import com.axilog.cov.enums.PurchaseStatusEnum;
import com.axilog.cov.repository.PoStatusRepository;
import com.axilog.cov.repository.SequenceRepository;
import com.axilog.cov.security.SecurityUtils;
import com.axilog.cov.service.InventoryService;
import com.axilog.cov.service.MailService;
import com.axilog.cov.service.OutletService;
import com.axilog.cov.service.PurchaseOrderQueryService;
import com.axilog.cov.service.PurchaseOrderService;
import com.axilog.cov.service.dto.PurchaseOrderCriteria;
import com.axilog.cov.service.pdf.PdfService;
import com.axilog.cov.util.DateUtil;
import com.axilog.cov.web.rest.errors.BadRequestAlertException;
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

    private final PurchaseOrderQueryService purchaseOrderQueryService;

    @Autowired
    private InventoryService inventoryService;
    
    @Autowired
    private MailService mailService;
    
    @Autowired
    private PdfService pdfService;
    
    @Autowired
    private InventoryMapper inventoryMapper;
    
    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;
    
    @Autowired
    private PoStatusRepository poStatusRepository;
    
    @Autowired
    private SequenceRepository sequenceRepository;
    
    @Autowired
    private OutletService outletService;
    
    @Value("${poEmailReceiver}")
    private String[] poEmailReceiver;
    
    @Value("${poSubjectEmail}")
    private String poSubjectEmail;
    
    @Value("${poEmailApprovalLevel1}")
    private String[] poEmailApprovalLevel1;
    
    @Value("${poEmailApprovalLevel2}")
    private String[] poEmailApprovalLevel2;
    
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
    
    @Scheduled(cron = "${autoReplenishCron}")
    public void scheduleTaskForAutoReplunish() throws IOException, DocumentException {
        long now = System.currentTimeMillis() / 1000;
        log.info("schedule tasks For Auto Replunish using cron jobs - " + now);
        List<Inventory> inventories = getProductToBeOrdered();
        List<PurchaseOrder> orders = purchaseOrderService.findAll();
        List<Outlet> outlets = outletService.findAll();
        
        
        
        
        outlets.forEach(outlet -> {
		try {
			List<Product> productsHavePo = new ArrayList<>();
	        if (orders != null) {
	        	orders.forEach(order -> {
	        		if (!order.getPoStatuses().contains(PoStatus.builder().status("CLOSED").build()) && order.getOutlet()!=null && order.getOutlet().equals(outlet)) {
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
			if (inventories != null) {
	        	productsToBeInPo = inventories.stream().map(Inventory ::  getProduct).filter(product -> !productsHavePo.contains(product)).collect(Collectors.toList());
	        }
			List<Inventory> inventoriesPerOutlet = inventories.stream().filter(inventory -> inventory.getOutlet().equals(outlet)).collect(Collectors.toList());
    		PoPdfDetail detail = inventoryMapper.toPdfListDetail(inventoriesPerOutlet, productsToBeInPo, outlet, currVal);
            if (detail == null || detail.getListDetails() == null || detail.getListDetails().isEmpty()) {
            	log.info("No Po to be ordered");
            }
            else {
            	File poPdf = pdfService.generatePdf(detail);
        		byte[] fileContent = FileUtils.readFileToByteArray(poPdf);
                List<Product> products = new ArrayList<>();
                if (inventories != null) {
                	products = inventories.stream().map(Inventory:: getProduct).collect(Collectors.toList());
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
                
                PoStatus poPending1Status = PoStatus.builder().status(PurchaseStatusEnum.PENDING_AP1.getLabel()).updatedAt(nowDate).build();
                poPending1Status = poStatusRepository.save(poPending1Status);
                poStatusList.add(poPending1Status);
               
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:MM:SS");
                PurchaseOrder po = PurchaseOrder.builder()
        				.products(products)
        				.createdAt(DateUtil.now())
        				.createdBy(login)
        				.deliveredDate(sdf.parse(detail.getHeaderPdfDetail().getDueDate()))
        				.poStatuses(poStatusList)
        				.orderNo(currVal)
        				.data(fileContent)
        				.outlet(outlet)
        				.build();
        		PurchaseOrder result = purchaseOrderService.save(po);
        		POMailDetail poMailDetail = POMailDetail.builder()
        				.poNumber(currVal)
        				.status(PurchaseStatusEnum.PENDING_AP2.getLabel())
        				.approvalLevel(1)
        				.emailToBeSent(true)
        				.build();
        		Context context = pdfService.getContext(poMailDetail, "mailDetail");
        		String html = pdfService.loadAndFillTemplate(context, "mail/poApprovalEmail");
        		mailService.sendEmailWithAttachmentAndMultiple(poEmailApprovalLevel1, poSubjectEmail, html, true, true, poPdf);
            }
			
		}
		catch(Exception e) {
			log.error("Exception when looping over inventory to create their Po", e);
		}
		
	});
        	 
        
    }
    
    
    @PostMapping("/purchaseOrders/approval")
    @Transactional
    public void approvePurchaseOrder(@RequestBody PurchaseOrderCommand purchaseOrderCommand) throws URISyntaxException, IOException {
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
            
            //
            if (purchaseOrderCommand.isEmailToBeSent()) {
            	
        		if (purchaseOrderCommand.getApprovalLevel() == 1) {
        			POMailDetail poMailDetail = POMailDetail.builder()
            				.poNumber(result.getOrderNo())
            				.status(PurchaseStatusEnum.SENT_TO_NUPCO.getLabel())
            				.approvalLevel(2)
            				.emailToBeSent(true)
            				.build();
        			Context context = pdfService.getContext(poMailDetail, "mailDetail");
            		String html = pdfService.loadAndFillTemplate(context, "mail/poApprovalEmail");
            		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-MM-SS");
            		File tempFile = File.createTempFile("order", sdf.format(DateUtil.now()));
            		FileOutputStream fos = new FileOutputStream(tempFile);
            		fos.write(result.getData());
            		fos.close();
        			mailService.sendEmailWithAttachmentAndMultiple(poEmailApprovalLevel2, poSubjectEmail, html, true, true, tempFile);	
        		}
        		else if (purchaseOrderCommand.getApprovalLevel() == 2) {
        			POMailDetail poMailDetail = POMailDetail.builder()
            				.poNumber(result.getOrderNo())
            				.status(PurchaseStatusEnum.SENT_TO_NUPCO.getLabel())
            				.approvalLevel(3)
            				.emailToBeSent(true)
            				.build();
        			Context context = pdfService.getContext(poMailDetail, "mailDetail");
            		String html = pdfService.loadAndFillTemplate(context, "mail/poApprovalEmail");
            		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
            		File tempFile = File.createTempFile("order", sdf.format(DateUtil.now()), null);
            		FileOutputStream fos = new FileOutputStream(tempFile);
            		fos.write(result.getData());
            		fos.close();
        			mailService.sendEmailWithAttachmentAndMultiple(poEmailReceiver, poSubjectEmail, html, true, true, tempFile);	
        		}
        		
            }
        }

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
         status.add("oos");
         status.add("noos");
         return inventoryService.findByStatusIn(status);
    }
}
