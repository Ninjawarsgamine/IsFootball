package com.isfootball.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Bean
    /**
    * Crea y devuelve una instancia de RestTemplate, que se utiliza para hacer solicitudes HTTP a 
    * otros servicios desde nuestra aplicación.
    * @return Una nueva instancia de RestTemplate.
    */
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
