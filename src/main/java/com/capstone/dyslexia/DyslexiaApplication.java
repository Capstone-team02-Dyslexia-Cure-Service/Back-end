package com.capstone.dyslexia;

import com.capstone.dyslexia.domain.level.config.LevelRangeConfig;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@EnableConfigurationProperties(LevelRangeConfig.class)
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class DyslexiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DyslexiaApplication.class, args);
	}

}
