package com.sprjjs.book;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * springboot启动器
 */
@SpringBootApplication
//扫描mapper层
@MapperScan(basePackages={"com.sprjjs.book.mapper"})
public class BookStoreApp {
    public static void main(String[] args) {
        SpringApplication.run(BookStoreApp.class,args);
    }

}
