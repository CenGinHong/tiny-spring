package org.springframework.test.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.stereotype.Component;

/**
 * @author chenJianhang
 * @version 1.0 2022/11/28 16:25
 */
@Component
public class Car {

    @Value("${brand}")
    private String brand;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                '}';
    }

}
