package com.axilog.cov.config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import com.axilog.cov.service.OtpMailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NoOpMailConfiguration {
    private final OtpMailService mockMailService;

    public NoOpMailConfiguration() {
        mockMailService = mock(OtpMailService.class);
        doNothing().when(mockMailService).sendActivationEmail(any());
    }

    @Bean
    public OtpMailService mailService() {
        return mockMailService;
    }
}
