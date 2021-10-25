package com.bstlr.starbux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ConfigurationPropertiesScan
@ComponentScan({"com.bstlr"})
public class StarbuxApplication {
	public static void main(String[] args) {
		SpringApplication.run(StarbuxApplication.class, args);
	}
}
