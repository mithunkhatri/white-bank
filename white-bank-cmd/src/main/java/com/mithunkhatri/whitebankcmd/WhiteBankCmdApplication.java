package com.mithunkhatri.whitebankcmd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "com.mithunkhatri")
@EnableEurekaClient
public class WhiteBankCmdApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhiteBankCmdApplication.class, args);
	}

}
