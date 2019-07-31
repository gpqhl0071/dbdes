package com.example.bddes;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.bddes.dao")
public class BddesApplication {

  public static void main(String[] args) {
    SpringApplication.run(BddesApplication.class, args);
  }

}
