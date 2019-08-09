package com.daisy.pirelli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PirelliApplication {

    public static void main(String[] args) {
        SpringApplication.run(PirelliApplication.class, args);
    }

}
