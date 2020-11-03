package com.uwplp.uwplp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UwplpApplication{
    public static final Logger log = LoggerFactory.getLogger(UwplpApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(UwplpApplication.class, args);
    }
}
