package com.weimob;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author itsNine
 * @create 2020-04-15-11:16
 */
@SpringBootApplication
@MapperScan("com.weimob.goods.mapper")
@EnableDubbo
public class MarketGoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(MarketGoodsApplication.class);
    }
}
