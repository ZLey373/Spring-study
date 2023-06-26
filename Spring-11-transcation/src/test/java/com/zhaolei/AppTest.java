package com.zhaolei;

import com.zhaolei.mapper.UserMapper;
import com.zhaolei.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class AppTest {
    @Test
    public void test() throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper usermapper = context.getBean("usermapper", UserMapper.class);
        for (User user : usermapper.selectUser()) {
            System.out.println(user);
        }

    }
}
