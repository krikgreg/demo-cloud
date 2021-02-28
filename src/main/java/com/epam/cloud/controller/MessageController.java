package com.epam.cloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {
    private static final String MESSAGE = "/message";

    @Value("${message.for.demo.cloud}")
    private String messageForDedo;

    @GetMapping(MESSAGE)
    public String checkHealth(){
        return messageForDedo;
    }
}
