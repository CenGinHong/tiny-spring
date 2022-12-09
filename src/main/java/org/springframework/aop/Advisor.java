package org.springframework.aop;

import org.aopalliance.aop.Advice;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/9 22:18
 */
public interface Advisor {

    Advice getAdvice();

}
