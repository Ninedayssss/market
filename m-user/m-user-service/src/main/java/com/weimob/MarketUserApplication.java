package com.weimob;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author itsNine
 * @create 2020-04-15-10:52
 */
@SpringBootApplication
@MapperScan("com.weimob.user.mapper")
@EnableDubbo
public class MarketUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(MarketUserApplication.class);
    }
}
