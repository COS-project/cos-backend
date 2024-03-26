package com.cos.cercat.batch;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;


@SpringBootApplication(scanBasePackages = "com.cos.cercat")
public class CercatBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(CercatBatchApplication.class, args);
	}

	@PostConstruct
	void started(){
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}
}
