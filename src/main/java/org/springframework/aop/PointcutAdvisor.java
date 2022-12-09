package org.springframework.aop;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/9 22:19
 */
public interface PointcutAdvisor extends Advisor {

    Pointcut getPointcut();

}
