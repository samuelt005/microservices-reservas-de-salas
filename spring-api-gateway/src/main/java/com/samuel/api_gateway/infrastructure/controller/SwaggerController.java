package com.samuel.api_gateway.infrastructure.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/swagger")
public class SwaggerController {

    @GetMapping("/config")
    public Map<String, String> swaggerConfig() {
        Map<String, String> services = new HashMap<>();
        services.put("Reserva Service", "/service-1/v3/api-docs");
        services.put("Sala Service", "/service-2/v3/api-docs");
        services.put("Usuario Service", "/service-3/v3/api-docs");
        return services;
    }
}
