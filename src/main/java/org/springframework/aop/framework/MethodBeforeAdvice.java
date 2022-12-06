package org.springframework.aop.framework;

import java.lang.reflect.Method;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/6 18:18
 */
public interface MethodBeforeAdvice extends BeforeAdvice {

    void before(Method method, Object[] args, Object target) throws Throwable;

}
