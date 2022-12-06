package org.springframework.test.common;

import org.springframework.aop.framework.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/6 19:31
 */
public class WorldServiceBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) {
        System.out.println("BeforeAdvice: do something before the earth explodes");
    }

}
