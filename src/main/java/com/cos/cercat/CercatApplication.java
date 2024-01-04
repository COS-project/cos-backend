package com.cos.cercat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CercatApplication {

	public static void main(String[] args) {
		SpringApplication.run(CercatApplication.class, args);
	}

}
