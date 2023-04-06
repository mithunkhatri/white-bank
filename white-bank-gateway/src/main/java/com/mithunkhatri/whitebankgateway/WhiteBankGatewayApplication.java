package com.mithunkhatri.whitebankgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class WhiteBankGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhiteBankGatewayApplication.class, args);
	}

} 
