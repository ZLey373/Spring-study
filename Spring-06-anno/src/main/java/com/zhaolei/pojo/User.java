package com.zhaolei.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component        //注解   相等于 <bean id="user" class="com.zhaolei.pojo.User"></bean>
@Scope("singleton")
public class User {
    @Value("zley")
    public String name;
}
