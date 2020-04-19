package com.cloud;

import com.cloud.spring.MatrixApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CasServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MatrixApplication.class, args);
    }

}
