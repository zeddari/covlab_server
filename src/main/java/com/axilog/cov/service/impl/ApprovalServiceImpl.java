package com.axilog.cov.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axilog.cov.domain.DynamicApprovalConfig;
import com.axilog.cov.repository.ApprovalRepository;
import com.axilog.cov.service.ApprovalService;

@Service
public class ApprovalServiceImpl implements ApprovalService {

	@Autowired
	private ApprovalRepository approvalRepository;
	
	@Override
	public List<DynamicApprovalConfig> findAll() {
		return approvalRepository.findAll();
	}

	@Override
	public DynamicApprovalConfig findStartStatus() {
		return approvalRepository.findByStartStatus(Boolean.TRUE);
	}

	@Override
	public DynamicApprovalConfig findEndStatus() {
		return approvalRepository.findByFinalStatus(Boolean.TRUE);
	}

	@Override
	public DynamicApprovalConfig findbyCurrentStatus(String currentStatus) {
		return approvalRepository.findByCurrentStepStatus(currentStatus);
	}

}
