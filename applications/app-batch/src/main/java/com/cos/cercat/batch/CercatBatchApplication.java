package com.cos.cercat.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.cos.cercat")
public class CercatBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(CercatBatchApplication.class, args);
	}

}
