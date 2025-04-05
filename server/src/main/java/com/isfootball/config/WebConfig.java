package com.isfootball.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") 
                .allowedMethods("GET", "POST", "PUT", "DELETE") 
                .allowedHeaders("*") 
                .allowCredentials(true); 
    }
}
//Clase de configuración de CORS para permitir peticiones desde el cliente (el
//cliente está "http://localhost:3000").