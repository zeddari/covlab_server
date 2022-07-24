package com.axilog.cov.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.axilog.cov.domain.Payment;

/**
 * Spring Data  repository for the Request entity.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {

	List<Payment> findByDriverNameOrSupervisorName(String driverName , String supervisorName );
	
	Payment findByInvoiceId(String invoiceId);
}
