package com.isfootball.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
//Activamos el caché para guardar información de las peticiones y así gastar
//menos peticiones de la API.

@ComponentScan(basePackages = {"com.isfootball"})
//Permite a Spring escanear los paquetes "server" y "controller" para detectar componentes
//como controladores, servicios, etc.

public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
