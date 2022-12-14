package org.springframework.test.ioc;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.bean.Car;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/13 20:55
 */
public class PackageScanTest {

    @Test
    public void testScanPackage() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:package-scan.xml");

        Car car = applicationContext.getBean("car", Car.class);
        assertThat(car).isNotNull();
    }

}
