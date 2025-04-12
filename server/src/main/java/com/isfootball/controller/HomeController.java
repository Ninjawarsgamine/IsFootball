package com.isfootball.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    /**
     * Función de prueba para conexión del back con el front.
     * @return Texto de prueba que se va a mostrar en el front.
     */
    public String home() {
        return "¡Bienvenido a IsFootball!";
    }
}
