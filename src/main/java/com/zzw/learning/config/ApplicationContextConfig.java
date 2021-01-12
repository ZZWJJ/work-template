package com.zzw.learning.config;

import com.zzw.learning.utils.SpringContextHolder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : zzw
 * @Description: TODO
 * @date : 2021/1/12 13:40
 **/
@Configuration
public class ApplicationContextConfig {

    public ApplicationContextConfig(){}

    @Bean
    @ConditionalOnMissingBean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

}
