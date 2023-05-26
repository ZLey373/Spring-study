package com.zhaolei.pojo;

public class User {
    private String name;

    public User(String name){
        this.name=name;
        System.out.println("有参构造对象");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void show(){
        System.out.println("姓名: "+name);
    }
}
