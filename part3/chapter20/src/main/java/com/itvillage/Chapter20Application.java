package com.itvillage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;


@EnableR2dbcRepositories
@EnableR2dbcAuditing
@SpringBootApplication
public class Chapter20Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter20Application.class, args);
    }

}
