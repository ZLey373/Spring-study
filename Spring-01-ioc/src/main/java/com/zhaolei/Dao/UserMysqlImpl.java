package com.zhaolei.Dao;

public class UserMysqlImpl implements UserDao{
    @Override
    public void getUser() {
        System.out.println("获取mysql数据！");
    }
}
