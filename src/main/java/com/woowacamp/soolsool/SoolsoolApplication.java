package com.woowacamp.soolsool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SoolsoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoolsoolApplication.class, args);
    }

}
