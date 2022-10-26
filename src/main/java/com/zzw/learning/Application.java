package com.zzw.learning;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author : zzw
 * @Description: TODO
 * @date : 2019/10/14 13:58
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"com.zzw"})
@MapperScan({"com.zzw.learning.mapper"})
public class Application {
//    @Autowired
//    private UserLogProducer kafkaSender;
//    @PostConstruct
//    public void init(){
//        for (int i = 0; i < 10; i++) {
//            //调用消息发送类中的消息发送方法
//            kafkaSender.sendLog(String.valueOf(i));
//        }
//    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
