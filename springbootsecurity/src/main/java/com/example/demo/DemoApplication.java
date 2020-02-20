package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@SpringBootApplication
@ServletComponentScan
@ImportResource(locations = "classpath:spring-mvc.xml")
@EnableAsync
@EnableRetry
@ComponentScan({"com.example.demo.service.impl"})
//加密数据库应该要密文存储
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
