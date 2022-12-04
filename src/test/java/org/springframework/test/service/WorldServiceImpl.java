package org.springframework.test.service;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/4 14:56
 */
public class WorldServiceImpl implements WorldService{
    @Override
    public void explode() {
        System.out.println("The Earth is going to explode");
    }
}
