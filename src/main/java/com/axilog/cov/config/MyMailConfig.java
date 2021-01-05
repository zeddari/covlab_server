package com.axilog.cov.config;

import java.util.Map;
import java.util.Properties;

import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MyMailConfig {
	
	/**
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = "spring.mail.otp")
	public MailProperties otpProperties()  {
		return new MailProperties();
	}
	
	/**
	 * @param otpProperties
	 * @return
	 */
	@Bean(name = "otpMailSender")
	@ConfigurationProperties(prefix = "spring.mail.otp")
	public JavaMailSender otpMailSender(MailProperties otpProperties) {
		JavaMailSenderImpl sender =  new JavaMailSenderImpl();
		this.applyProperties(sender, otpProperties);
		return sender;
    }
	
	
	/**
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = "spring.mail.po")
	public MailProperties poProperties()  {
		return new MailProperties();
	}
	
	/**
	 * @param poProperties
	 * @return
	 */
	@Bean(name = "poMailSender")
	@ConfigurationProperties(prefix = "spring.mail.po")
	public JavaMailSender poMailSender(MailProperties poProperties) {
		JavaMailSenderImpl sender =  new JavaMailSenderImpl();
		this.applyProperties(sender, poProperties);
		return sender;
    }
	
	private void applyProperties(JavaMailSenderImpl sender, MailProperties properties) {
		sender.setHost(properties.getHost());
		if (properties.getPort() != null) {
			sender.setPort(properties.getPort());
		}
		sender.setUsername(properties.getUsername());
		sender.setPassword(properties.getPassword());
		sender.setProtocol(properties.getProtocol());
		if (properties.getDefaultEncoding() != null) {
			sender.setDefaultEncoding(properties.getDefaultEncoding().name());
		}
		if (!properties.getProperties().isEmpty()) {
			sender.setJavaMailProperties(asProperties(properties.getProperties()));
		}
	}
	
	private Properties asProperties(Map<String, String> source) {
		Properties properties = new Properties();
		properties.putAll(source);
		return properties;
	}
}