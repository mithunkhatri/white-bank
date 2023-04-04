package com.mithunkhatri.whitebankquery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "com.mithunkhatri")
public class WhiteBankQueryApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhiteBankQueryApplication.class, args);
	}

}
