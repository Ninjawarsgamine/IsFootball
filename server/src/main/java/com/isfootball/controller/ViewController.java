package com.isfootball.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
    /**
     * Función que muestra el front compilado de Vue.js 3
     * @return Abre el index.html del front.
     */
    @RequestMapping("/")
    public String forward() {
        return "forward:/index.html";
    }
}
