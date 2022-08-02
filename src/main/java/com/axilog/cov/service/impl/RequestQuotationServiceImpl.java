package com.axilog.cov.service.impl;

import com.axilog.cov.domain.*;
import com.axilog.cov.dto.command.NewPoCommand;
import com.axilog.cov.repository.*;
import com.axilog.cov.service.OtpMailService;
import com.axilog.cov.service.RequestQuotationService;
import com.axilog.cov.service.pdf.PdfServiceQuotation;
import com.axilog.cov.util.DateUtil;
import com.lowagie.text.DocumentException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link RequestQuotation}.
 */
@Service
@Transactional
public class RequestQuotationServiceImpl implements RequestQuotationService {

    private final Logger log = LoggerFactory.getLogger(RequestQuotationServiceImpl.class);

    private final RequestQuotationRepository requestQuotationRepository;

    @Autowired
    private RequestSequenceRepository requestSequenceRepository;

    @Autowired
    private CurrentCustomerLocationRepository currentCustomerLocationRepository;

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private PdfServiceQuotation pdfServiceQuotation;
    
	@Autowired
	OtpMailService otpMailService;
    
    @Override
    public void generatePdf(String requestQuotationId) throws IOException, DocumentException {
        log.debug("generate PDF for RequestQuotation : {}", requestQuotationId);

        RequestQuotation requestQuotation =  requestQuotationRepository.findByRequestQuotationId(requestQuotationId);

        Object[] pdfService = pdfServiceQuotation.generatePdf(requestQuotation);
        File poPdf = (File)pdfService[1] ;
		byte[] invoice = FileUtils.readFileToByteArray(poPdf);
		requestQuotation.setPdfFile(invoice);
		requestQuotationRepository.save(requestQuotation);
		try {
			otpMailService.sendEmailWithAttachment(requestQuotation.getCustomerEmail(), "Quotation Created : " + DateUtil.dateTimeNow(DateUtil.MOI_DATE_ENCODING), (String)pdfService[0], true, true, poPdf);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    @Autowired
    private NewPoRepository newPoRepository;
    public RequestQuotationServiceImpl(RequestQuotationRepository requestQuotationRepository) {
        this.requestQuotationRepository = requestQuotationRepository;
    }

    @Override
    public RequestQuotation save(RequestQuotation requestQuotation) {
        log.debug("Request to save RequestQuotation : {}", requestQuotation);
        RequestQuotation requestQuotationInDb = requestQuotationRepository.save(requestQuotation);
        incrementQuotationSequence();
        return requestQuotationInDb;
    }


    @Override
    @Transactional(readOnly = true)
    public Page<RequestQuotation> findAll(Pageable pageable) {
        log.debug("Request to get all RequestQuotations");
        return requestQuotationRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<RequestQuotation> findOne(Long id) {
        log.debug("Request to get RequestQuotation : {}", id);
        return requestQuotationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RequestQuotation : {}", id);
        requestQuotationRepository.deleteById(id);
    }


	@Override
	public List<RequestQuotation> findAll() {
		return requestQuotationRepository.findAll();
	}

	@Override
	public Optional<RequestQuotation> findOne(Example<RequestQuotation> exampleRequestQuotation) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<RequestQuotation> findByRequestQuotationId(Long requestQuotationId) {
		return requestQuotationRepository.findByRequestQuotationId(requestQuotationId);

	}

    @Override
    public Long getNextRequestQuotationSequence() {
        RequestSequence requestSequence = requestSequenceRepository.curVal();
        if (Optional.ofNullable(requestSequence).isPresent()) {
            Long currentVal = requestSequence.getCurrentNumber();
            return currentVal;
        }
        return -1L;
    }

    @Override
    public RequestSequence getNextRequestQuotationSequenceObject() {
        return requestSequenceRepository.curVal();
    }

    @Override
    public void incrementQuotationSequence() {
        RequestSequence requestSequence = requestSequenceRepository.curVal();
        requestSequence.setCurrentNumber(requestSequence.getCurrentNumber() + 1);
        requestSequenceRepository.save(requestSequence);
    }

    @Override
    public void saveLocation(CurrentCustomerLocation currentCustomerLocation) {
        List<CurrentCustomerLocation> currentCustomerLocationList = currentCustomerLocationRepository.findByQuotationId(currentCustomerLocation.getQuotationId());
        if (Optional.ofNullable(currentCustomerLocationList).isPresent() && currentCustomerLocationList.size()> 0) {
            CurrentCustomerLocation currentCustomerLocationInDb = currentCustomerLocationList.get(0);
            currentCustomerLocationInDb.setLat(currentCustomerLocation.getLat());
            currentCustomerLocationInDb.setLng(currentCustomerLocation.getLng());
            currentCustomerLocationRepository.save(currentCustomerLocationInDb);
        }
        else {
            currentCustomerLocationRepository.save(currentCustomerLocation);
        }
    }

    @Override
    public void createUpdateLocationNotif(String lat, String lng, String quotationId) {
        String title = "New Location Update for Quotation: "+quotationId;
        String description = "The latitude is: "+lat+", the longitude is: "+lng;
        Notification notification = Notification.builder().notifTitle(title).notifDescription(description).updateAt(new Date()).build();
        notificationRepository.save(notification);
    }

    /**
     *
     * @return
     */
    @Override
    public List<Notification> getTop5Notif() {
        List<Notification> notifications = notificationRepository.findAllByOrderByUpdateAtDesc();
        return notifications;
    }


    /**
     *
     * @param newPoCommand
     */
    @Override
    public void saveNewPo(NewPoCommand newPoCommand) {
        List<NewPo> newPoList = newPoRepository.findByQuotationIdAndAndPoId(newPoCommand.getQuotationId(), newPoCommand.getPoNumber());
        if (newPoList != null && !newPoList.isEmpty()) {
            NewPo newPoInDb = newPoList.get(0);
            newPoInDb.setPoAmount(newPoInDb.getPoAmount());
            newPoRepository.save(newPoInDb);
        }
        else {
            NewPo newPo = NewPo.builder().quotationId(newPoCommand.getQuotationId()).poAmount(newPoCommand.getPoAmount()).poId(newPoCommand.getPoNumber()).build();
            newPoRepository.save(newPo);
        }

    }
}
