package com.zhaolei;

import com.zhaolei.Dao.UserDao;
import com.zhaolei.Dao.UserDaoImpl;
import com.zhaolei.Dao.UserMysqlImpl;
import com.zhaolei.Service.UserService;
import com.zhaolei.Service.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Unit test for simple App.
 */
public class AppTest
{
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        UserService serviceImpl = (UserService) context.getBean("ServiceImpl");
        serviceImpl.getUser();
    }
}
