package com.cos.cercat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {
		"com.cos.cercat.domain.common",
		"com.cos.cercat.alarm",
		"com.cos.cercat.domain.certificate",
		"com.cos.cercat.domain.user",
		"com.cos.cercat.domain.alarm",
		"com.cos.cercat.database.certificate",
		"com.cos.cercat.database.user",
		"com.cos.cercat.database.alarm",
		"com.cos.cercat.database.common",
		"com.cos.cercat.redis.user",
		"com.cos.cercat.security",
		"com.cos.cercat.web",
		"com.cos.cercat.exception",
})
public class CercatAlarmApplication {

	public static void main(String[] args) {
		SpringApplication.run(CercatAlarmApplication.class, args);
	}

}
