package com.daisy.bangsen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BangsenApplication {

    public static void main(String[] args) {
        SpringApplication.run(BangsenApplication.class, args);
    }

}
