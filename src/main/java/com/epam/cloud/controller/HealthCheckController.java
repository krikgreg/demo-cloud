package com.epam.cloud.controller;

import com.epam.cloud.data.Health;
import com.epam.cloud.service.imp.HealthCheckServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class HealthCheckController {
    private static final String HEALTH = "/health";

    @Autowired
    private HealthCheckServiceImpl healthCheckService;

    @GetMapping(HEALTH)
    public Health checkHealth(){
        return healthCheckService.checkHealth();
    }
}
