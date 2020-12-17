package com.axilog.cov.service.impl;

import com.axilog.cov.service.TicketsService;
import com.axilog.cov.domain.Tickets;
import com.axilog.cov.repository.TicketsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Tickets}.
 */
@Service
@Transactional
public class TicketsServiceImpl implements TicketsService {

    private final Logger log = LoggerFactory.getLogger(TicketsServiceImpl.class);

    private final TicketsRepository ticketsRepository;

    public TicketsServiceImpl(TicketsRepository ticketsRepository) {
        this.ticketsRepository = ticketsRepository;
    }

    @Override
    public Tickets save(Tickets tickets) {
        log.debug("Request to save Tickets : {}", tickets);
        return ticketsRepository.save(tickets);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Tickets> findAll(Pageable pageable) {
        log.debug("Request to get all Tickets");
        return ticketsRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Tickets> findOne(Long id) {
        log.debug("Request to get Tickets : {}", id);
        return ticketsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tickets : {}", id);
        ticketsRepository.deleteById(id);
    }
}
