package com.aarogyasetu.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;

@Configuration
@ComponentScan(basePackages = "com.aarogyasetu.config")
@Order(1)
public class SpringConfig {

	@Primary
	@Bean
	public ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}
}
