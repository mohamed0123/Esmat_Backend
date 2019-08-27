package com.example.easynotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EsmatApplication {

	// https://www.callicoder.com/spring-boot-rest-api-tutorial-with-mysql-jpa-hibernate/
	// https://github.com/callicoder/spring-boot-mysql-rest-api-tutorial
	public static void main(String[] args) {
		SpringApplication.run(EsmatApplication.class, args);
	}
}
