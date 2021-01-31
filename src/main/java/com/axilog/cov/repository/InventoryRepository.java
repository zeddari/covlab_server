package com.axilog.cov.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.axilog.cov.domain.Inventory;
import com.axilog.cov.domain.Outlet;
import com.axilog.cov.domain.Product;

/**
 * Spring Data  repository for the Inventory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long>, JpaSpecificationExecutor<Inventory> {

	List<Inventory> findByStatusIn(List<String> status);
	List<Inventory> findByStatusInAndIsLastInstance(List<String> status, Boolean isLastInstance);
	List<Inventory> findByStatusInAndIsLastInstanceAndOutlet(List<String> status, Boolean isLastInstance, Outlet outlet);
	List<Inventory> findByStatusInAndIsLastInstanceAndCapacityLessThan(List<String> status, Boolean isLastInstance, Double capapcity);
	List<Inventory> findByOutletAndProductAndIsLastInstance(Outlet outlet, Product product, Boolean isLastInstance);
	List<Inventory> findByLastUpdatedAtBetween(Date lastUpdatedAtstart, Date lastUpdatedAtend);
	
	List<Inventory> findByOutletOutletId(Long outletId);
	
//	@Query(value = "select * from inventory where ", nativeQuery = true)
//	List<Inventory> findByLastUpdatedAtBetweenTwoDate(Date lastUpdatedAtstart, Date lastUpdatedAtend);
}
