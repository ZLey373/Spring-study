package com.zhaolei;

import com.zhaolei.Dao.UserDaoImpl;
import com.zhaolei.Dao.UserMysqlImpl;
import com.zhaolei.Service.UserServiceImpl;


/**
 * Unit test for simple App.
 */
public class AppTest
{
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDao(new UserDaoImpl());
        userService.getUser();
    }
}
