package com.salon.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class HomeController {
    @GetMapping("/")

    public String HomeControllerHandler() {
        return "User microservice for Saloon booking system";
    }
}
