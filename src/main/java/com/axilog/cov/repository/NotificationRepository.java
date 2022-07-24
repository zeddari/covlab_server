package com.axilog.cov.repository;

import com.axilog.cov.domain.Category;
import com.axilog.cov.domain.CategoryType;
import com.axilog.cov.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Category entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>, JpaSpecificationExecutor<Category> {
    public List<Notification> findAllByOrderByUpdateAtDesc();
}
