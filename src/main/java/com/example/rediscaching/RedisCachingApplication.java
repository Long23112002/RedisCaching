package com.example.rediscaching;

import com.example.rediscaching.domain.services.ProductService;
import com.example.rediscaching.domain.services.impl.ProductServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class RedisCachingApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisCachingApplication.class, args);
    }
//
//    @Bean
//    public CommandLineRunner init(ProductServiceImpl dataInitService) {
//        return args -> {
//            dataInitService.createProducts(10000);
//            System.out.println("Generated 1000 products.");
//        };
//    }

}
