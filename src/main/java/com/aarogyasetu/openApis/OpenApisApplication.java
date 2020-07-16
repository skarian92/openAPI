package com.aarogyasetu.openApis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.aarogyasetu")
@SpringBootApplication
public class OpenApisApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenApisApplication.class, args);
	}

}
