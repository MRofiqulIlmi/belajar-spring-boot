package com.example.logicalquizmanual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class LogicalQuizManualApplication {
    public static void main(String[] args){
        SpringApplication.run(LogicalQuizManualApplication.class);

        System.out.println("test aja");
    }
}