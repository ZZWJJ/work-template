package com.zzw.learning;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author : zzw
 * @Description: TODO
 * @date : 2019/10/14 13:58
 **/
@SpringBootApplication
@MapperScan({"com.zzw.learning.mapper"})
public class BasicDataApplication {
    public static void main(String[] args) {
        SpringApplication.run(BasicDataApplication.class, args);
    }
}
