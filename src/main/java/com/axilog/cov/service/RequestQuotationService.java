package com.axilog.cov.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.axilog.cov.domain.*;
import com.axilog.cov.dto.command.NewPoCommand;
import com.lowagie.text.DocumentException;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RequestQuotationService {

	/**
     * Save a RequestQuotation.
     *
     * @param RequestQuotation the entity to save.
     * @return the persisted entity.
     */

    /**
     * Get all the RequestQuotations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RequestQuotation> findAll(Pageable pageable);

    /**
     * @return
     */
    List<RequestQuotation> findAll();


    /**
     * Get the "id" RequestQuotation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RequestQuotation> findOne(Long id);


    /**
     * @param exampleRequestQuotation
     * @return
     */
    Optional<RequestQuotation> findOne(Example<RequestQuotation> exampleRequestQuotation);


    /**
     * Delete the "id" RequestQuotation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<RequestQuotation> findByRequestQuotationId(Long requestQuotationId);

	RequestQuotation save(RequestQuotation requestQuotation);


    Long getNextRequestQuotationSequence();

    RequestSequence getNextRequestQuotationSequenceObject();

    void incrementQuotationSequence();

    void saveLocation(CurrentCustomerLocation currentCustomerLocation);

    void createUpdateLocationNotif(String lat, String lng, String quotationId);

    List<Notification> getTop5Notif();

    void saveNewPo(NewPoCommand newPoCommand);

	void generatePdf(String requestQuotationId) throws IOException, DocumentException;
}
