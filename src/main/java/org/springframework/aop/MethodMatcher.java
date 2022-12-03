package org.springframework.aop;

import java.lang.reflect.Method;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/3 16:41
 */
public interface MethodMatcher {

    boolean matches(Method method, Class<?> targetClass);

}
