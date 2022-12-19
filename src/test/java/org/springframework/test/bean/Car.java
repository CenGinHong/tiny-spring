package org.springframework.test.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.stereotype.Component;

import java.time.LocalDate;

/**
 * @author chenJianhang
 * @version 1.0 2022/11/28 16:25
 */
@Component
public class Car {

    private int price;

    private LocalDate produceDate;

    @Value("${brand}")
    private String brand;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDate getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(LocalDate produceDate) {
        this.produceDate = produceDate;
    }

    @Override
    public String toString() {
        return "Car{" +
                "price=" + price +
                ", produceDate=" + produceDate +
                ", brand='" + brand + '\'' +
                '}';
    }
}
