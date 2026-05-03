package com.example.backend.infrastructure.adapter.in.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

@GetMapping("/api/hello")
public void hello() {
    System.out.println("Petición recibida desde Angular");
}
}
