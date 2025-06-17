package com.mvp.customerprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableCaching
@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.mvp.customerprocessor.repository")
public class CustomerQueryProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerQueryProcessorApplication.class, args);
    }

}
