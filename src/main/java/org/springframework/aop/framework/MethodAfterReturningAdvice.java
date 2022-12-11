package org.springframework.aop.framework;

import java.lang.reflect.Method;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/6 20:14
 */
public interface MethodAfterReturningAdvice extends AfterReturningAdvice {

    void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable;

}
