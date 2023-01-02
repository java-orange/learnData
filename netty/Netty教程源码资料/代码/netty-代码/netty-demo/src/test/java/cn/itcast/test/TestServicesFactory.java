package cn.itcast.test;

import cn.itcast.server.service.HelloService;
import cn.itcast.server.service.ServicesFactory;

public class TestServicesFactory {
    public static void main(String[] args) {
        HelloService service = ServicesFactory.getService(HelloService.class);
        System.out.println(service.sayHello("hi"));
    }
}
