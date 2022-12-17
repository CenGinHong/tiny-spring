package org.springframework.test.aop;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.service.WorldService;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/10 21:53
 */
public class AutoProxyTest {

    @Test
    public void testAutoProxy() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:auto-proxy.xml");

        //获取代理对象
        WorldService worldService = applicationContext.getBean("worldService", WorldService.class);
        worldService.explode();
    }

    @Test
    public void testPopulateProxyBeanWithPropertyValues() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:populate-proxy-bean-with-property-values.xml");

        //获取代理对象
        WorldService worldService = applicationContext.getBean("worldService", WorldService.class);
        worldService.explode();
        assertThat(worldService.getName()).isEqualTo("earth");
    }

}
