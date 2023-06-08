package com.zhaolei.config;

import com.zhaolei.pojo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZLConfig {

    @Bean
    public User user(){
        return new User();
    }

}
