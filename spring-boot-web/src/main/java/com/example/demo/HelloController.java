package com.example.demo;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Random;

@RestController
public class HelloController {

    private final RestTemplate restTemplate;
    private final Random random = new Random();

    public HelloController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @GetMapping
    public String sayHello() {
        final URI uri = URI.create("http://192.168.68.117:7070/LICENSE?" + random.nextDouble());
        final String response = restTemplate.getForObject(uri, String.class);
        return response;
    }

}
