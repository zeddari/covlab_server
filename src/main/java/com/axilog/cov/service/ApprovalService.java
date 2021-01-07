package com.axilog.cov.service;

import java.util.List;

import com.axilog.cov.domain.Category;
import com.axilog.cov.domain.DynamicApprovalConfig;

/**
 * Service Interface for managing {@link Category}.
 */
public interface ApprovalService {

    List<DynamicApprovalConfig> findAll();
    DynamicApprovalConfig findStartStatus();
    DynamicApprovalConfig findEndStatus();
    DynamicApprovalConfig findbyCurrentStatus(String currentStatus);
}
