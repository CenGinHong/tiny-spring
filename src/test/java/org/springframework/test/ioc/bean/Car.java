package org.springframework.test.ioc.bean;

/**
 * @author chenJianhang
 * @version 1.0 2022/11/28 16:25
 */
public class Car {

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
