package org.springframework.test.ioc;

import org.junit.Test;
import org.springframework.bean.factory.config.BeanDefinition;
import org.springframework.bean.factory.support.DefaultListableBeanFactory;

/**
 * @author chenJianhang
 * @version 1.0 2022/11/27 21:46
 */
public class BeanDefinitionAndBeanDefinitionRegistryTest {

    @Test
    public void testBeanFactory() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinition beanDefinition = new BeanDefinition(HelloService.class);
        beanFactory.registerBeanDefinition("helloService", beanDefinition);

        HelloService helloService = (HelloService) beanFactory.getBean("helloService");
        helloService.sayHello();
    }

}
