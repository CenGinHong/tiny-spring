package org.springframework.aop;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/3 16:38
 */
public interface ClassFilter {

    boolean matches(Class<?> clazz);

}
