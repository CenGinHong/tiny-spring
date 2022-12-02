package org.springframework.test.ioc.common;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.test.ioc.bean.Car;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/2 20:24
 */
public class CarFactoryBean implements FactoryBean<Car> {

    private String brand;

    @Override
    public Car getObject() {
        Car car = new Car();
        car.setBrand(brand);
        return car;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

}
