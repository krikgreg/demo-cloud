package com.epam.cloud.controller;

import com.epam.cloud.service.HealthCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class HealthCheckController {
    private static final String HEALTH = "/health";

    @Autowired
    private HealthCheckService healthCheckService;

    @GetMapping(HEALTH)
    public Health checkHealth(){
        return healthCheckService.health();
    }
}
