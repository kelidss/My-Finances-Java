package com.api.my_finances;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing  
public class MyFinancesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyFinancesApplication.class, args);
    }
}
