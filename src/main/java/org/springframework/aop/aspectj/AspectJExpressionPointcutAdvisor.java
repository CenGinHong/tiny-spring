package org.springframework.aop.aspectj;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/9 22:20
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {


    private AspectJExpressionPointcut pointcut;

    private Advice advice;

    public void setExpression(String expression) {
        pointcut = new AspectJExpressionPointcut(expression);
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }
}
