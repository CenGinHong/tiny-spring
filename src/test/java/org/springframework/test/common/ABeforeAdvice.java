package org.springframework.test.common;

import org.springframework.aop.framework.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/19 20:50
 */
public class ABeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {

    }
}
