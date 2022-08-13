package com.axilog.cov.service.impl;

import com.axilog.cov.domain.Authority;
import com.axilog.cov.domain.User;
import com.axilog.cov.repository.UserRepository;
import com.axilog.cov.service.DriversService;
import com.axilog.cov.domain.Drivers;
import com.axilog.cov.repository.DriversRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Drivers}.
 */
@Service
@Transactional
public class DriversServiceImpl implements DriversService {

    private final Logger log = LoggerFactory.getLogger(DriversServiceImpl.class);

    private final DriversRepository driversRepository;

    @Autowired
    private UserRepository userRepository;

    public DriversServiceImpl(DriversRepository driversRepository) {
        this.driversRepository = driversRepository;
    }

    @Override
    public Drivers save(Drivers drivers) {
        log.debug("Request to save Drivers : {}", drivers);
        return driversRepository.save(drivers);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Drivers> findAll(Pageable pageable) {
        log.debug("Request to get all Drivers");
        return driversRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Drivers> findOne(Long id) {
        log.debug("Request to get Drivers : {}", id);
        return driversRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tickets : {}", id);
        driversRepository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        Authority authority = Authority.builder().name("ROLE_DRIVER").build();
        return userRepository.findByAuthorities(authority);
    }
}
