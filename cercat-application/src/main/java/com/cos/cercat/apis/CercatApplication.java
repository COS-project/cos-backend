package com.cos.cercat.apis;

import com.cos.cercat.common.CommonRoot;
import com.cos.cercat.domain.DomainRoot;
import com.cos.cercat.infra.InfraRoot;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;


@SpringBootApplication(
		scanBasePackageClasses = { ApiRoot.class, DomainRoot.class, InfraRoot.class, CommonRoot.class }
)
public class CercatApplication {

	public static void main(String[] args) {
		SpringApplication.run(CercatApplication.class, args);
	}

	@PostConstruct
	void started(){
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}
}
