package com.axilog.cov.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.axilog.cov.domain.Outlet;

/**
 * Spring Data  repository for the Outlet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OutletRepository extends JpaRepository<Outlet, Long>, JpaSpecificationExecutor<Outlet> {

	List<Outlet> findByOutletRegion(String outletRegion);
	List<Outlet> findByOutletParentRegion(String outletParentRegion);
	List <Outlet>  findByOutletName(String outletName);
	
}
