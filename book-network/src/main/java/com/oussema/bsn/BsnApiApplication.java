package com.oussema.bsn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef="auditorAware")
@EnableAsync
public class BsnApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BsnApiApplication.class, args);
	}

}
