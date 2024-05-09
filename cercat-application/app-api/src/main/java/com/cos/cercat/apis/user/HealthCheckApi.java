package com.cos.cercat.apis.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthCheckApi {

    public String healthCheck() {
        return "health check success";
    }
}
