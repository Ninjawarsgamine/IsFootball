package com.isfootball.config;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isfootball.utils.Utils;

@Configuration
@Getter
@Setter
public class AppConfig {

    @Value("${api.key}")
    private String apiKey;

    @Value("${api.host}")
    private String apiHost;

    @Value("${season}")
    private String season;

    /**
     * Crea y devuelve una instancia de RestTemplate, que se utiliza para hacer solicitudes HTTP a 
     * otros servicios desde nuestra aplicación.
     * @return Una nueva instancia de RestTemplate.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Utils utils(RestTemplate restTemplate, ObjectMapper objectMapper, AppConfig appConfig) {
        return new Utils(restTemplate, objectMapper, appConfig);
    }
}