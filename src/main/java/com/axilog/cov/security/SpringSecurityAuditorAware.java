package com.axilog.cov.security;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;


import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link AuditorAware} based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

//    @Override
//    public Optional<String> getCurrentAuditor() {
//        return Optional.of(SecurityUtils.getCurrentUserLogin().orElse(Constants.SYSTEM_ACCOUNT));
//    }
	
	private final HttpServletRequest httpServletRequest;

	public SpringSecurityAuditorAware(HttpServletRequest httpServletRequest) {
	    this.httpServletRequest = httpServletRequest;
	}

	@Override
	public Optional<String> getCurrentAuditor() {
	    return Optional.ofNullable(httpServletRequest.getUserPrincipal())
	            .map(Principal::getName);
	}
}
