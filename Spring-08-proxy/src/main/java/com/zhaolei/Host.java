package com.zhaolei;

//房东
public class Host implements Rent{

    @Override
    public void rent() {
        System.out.println("房东出租房子");
    }
}
