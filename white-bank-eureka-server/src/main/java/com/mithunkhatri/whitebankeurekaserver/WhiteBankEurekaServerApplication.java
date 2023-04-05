package com.mithunkhatri.whitebankeurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class WhiteBankEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhiteBankEurekaServerApplication.class, args);
	}

}
