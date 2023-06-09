package com.zhaolei;

public class Proxy implements Rent{
    private Host host;

    public Proxy(Host host) {
        this.host = host;
    }

    public Proxy() {
    }

    @Override
    public void rent() {
        host.rent();
        seeHouse();
        Contract();
        fare();
    }
    public void seeHouse(){
        System.out.println("中介带着看房子");
    }
    public void fare(){
        System.out.println("中介收取中介费用");
    }
    public void Contract(){
        System.out.println("签租赁合同");
    }
}
