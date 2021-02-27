package com.juliajiang.exportdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.juliajiang.exportdemo.*.domain.mapper")
public class ExportDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExportDemoApplication.class, args);
    }

}
