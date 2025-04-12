package com.isfootball.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "http://localhost:3000")
public class TestController {

    @GetMapping
    public String getTestMessage() {
    	return "Bienvenido a IsFootball";
    }
}
