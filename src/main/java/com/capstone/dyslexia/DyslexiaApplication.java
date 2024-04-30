package com.capstone.dyslexia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class DyslexiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DyslexiaApplication.class, args);
	}

}
