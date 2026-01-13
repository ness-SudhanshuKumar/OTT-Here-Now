package com.sudhanshu.reportservice;


import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ReportServiceApplication {

    private static final Logger log = LoggerFactory.getLogger(ReportServiceApplication.class);

	public static void main(String[] args) {
		 log.debug("Creating user: {}");
	       log.info("User created successfully with ID: {}");
	       log.error("Failed to create user");
		SpringApplication.run(ReportServiceApplication.class, args);
	}

}
