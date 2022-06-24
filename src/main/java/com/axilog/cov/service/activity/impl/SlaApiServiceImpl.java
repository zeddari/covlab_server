package com.axilog.cov.service.activity.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axilog.cov.domain.Request;
import com.axilog.cov.domain.Sla;
import com.axilog.cov.exception.RequestNotFoundException;
import com.axilog.cov.repository.RequestRepository;
import com.axilog.cov.repository.SlaRepository;
import com.axilog.cov.service.activity.api.SlaApiService;
import com.axilog.cov.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author a.zeddari
 *
 */
@Service
@Slf4j
public class SlaApiServiceImpl implements SlaApiService {

	@Autowired
	private SlaRepository slaRepository;
	
	@Autowired
	private RequestRepository requestRepository;
	
	
	@Override
	public void startSla(String requestId, String context) {
		log.info("the start sla in the sLa service is called, requestId {}, context {}", requestId, context);
		Request request = requestRepository.findByRequestId(requestId);
		if (request != null) {
			Sla sla = Sla.builder()
					.context(context)
					.request(request)
					.dueDateTime(DateUtil.now())
					.build();
			slaRepository.save(sla);
		}
		

	}

	@Override
	public void endSla(String requestId, String context) throws RequestNotFoundException {
		log.info("the end sla in the sLa service is called, requestId {}, context {}", requestId, context);
		Request request = requestRepository.findByRequestId(requestId);
		if (request == null) {
			throw new RequestNotFoundException(requestId);
		}
		Sla sla = slaRepository.findByRequestIdAndContext(request.getId(), context);
		if (sla != null) {
			sla.setEndDateTime(DateUtil.now());
		}
	}
	
	@Override
	public void endSla(String requestId, String context, Boolean outOfSla) {
		log.info("the end sla in the sLa service is called, requestId {}, context {}, outOfSla {}",
				requestId, context, outOfSla);
		Request request = requestRepository.findByRequestId(requestId);
		Sla sla = slaRepository.findByRequestIdAndContext(request.getId(), context);
		if (sla != null) {
			sla.setEndDateTime(DateUtil.now());
		}
		if (outOfSla != null) {
			sla.setOutOfSla(outOfSla);
		}
		this.calculSlaDuration(sla);
	}

	private Sla calculSlaDuration(final Sla sla) {
		long diff =   DateUtil.convertToDateViaUtilDate(DateUtil.convertToLocalDateViaInstant(sla.getStartDateTime())).getTime() - DateUtil.convertToDateViaUtilDate(DateUtil.convertToLocalDateViaInstant(sla.getEndDateTime())).getTime();
		long seconds = Math.abs(TimeUnit.MILLISECONDS.toSeconds(diff));
		log.info("Calculate duration for SLA");
		sla.setDuration(seconds == -1 ? 0 : seconds);
		return sla;
	}
}
