package com.uwplp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class UwplpApplication{
    public static final Logger log = LoggerFactory.getLogger(UwplpApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(UwplpApplication.class, args);
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/v1/auth/userinfo")
                        .allowedOrigins("http://localhost:3000")
                        .allowCredentials(true);
                registry.addMapping("/only_vendor")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST")
                        .allowCredentials(true);
                registry.addMapping("/add_product")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST")
                        .allowCredentials(true);
                registry.addMapping("/api/v1/auth/login")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}
