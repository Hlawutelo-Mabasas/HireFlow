package com.hireflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HireFlowApplication {
    public static void main(String[] args) {
        SpringApplication.run(HireFlowApplication.class, args);
        System.out.println("HireFlow Backend Started Successfully");
    }
}
