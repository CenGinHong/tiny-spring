package org.springframework.aop;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/3 16:42
 */
public interface Pointcut {

    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();

}
