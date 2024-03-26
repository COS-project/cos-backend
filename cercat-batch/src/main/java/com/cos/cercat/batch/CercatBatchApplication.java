package com.cos.cercat.batch;

import com.cos.cercat.common.CommonRoot;
import com.cos.cercat.domain.DomainRoot;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;


@SpringBootApplication(scanBasePackageClasses = { BatchRoot.class, DomainRoot.class, CommonRoot.class })
public class CercatBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(CercatBatchApplication.class, args);
	}

	@PostConstruct
	void started(){
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}
}
