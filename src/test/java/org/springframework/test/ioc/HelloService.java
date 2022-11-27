package org.springframework.test.ioc;

/**
 * @author chenJianhang
 * @version 1.0 2022/11/27 21:46
 */
public class HelloService {

    public String sayHello() {
        System.out.println("hello");
        return "hello";
    }

}
