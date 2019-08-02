package com.example.bddes;

import com.example.bddes.config.CommonConfig;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@MapperScan("com.example.bddes.dao")
@EnableConfigurationProperties({CommonConfig.class})
public class BddesApplication {

  public static void main(String[] args) {
    SpringApplication.run(BddesApplication.class, args);
  }

}
