package com.zhaolei;

import com.zhaolei.config.ZLConfig;
import com.zhaolei.pojo.User;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Unit test for simple App.
 */
public class AppTest {
    @Test
    public void test(){
        ApplicationContext context = new AnnotationConfigApplicationContext(ZLConfig.class);
        User user = context.getBean("user", User.class);
        System.out.println(user.getName());
    }

}
