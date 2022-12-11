package org.springframework.aop.framework.adapter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.MethodAfterReturningAdvice;
import org.springframework.aop.framework.MethodBeforeAdvice;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/6 18:16
 */
public class MethodAdviceInterceptor implements MethodInterceptor {

    private MethodBeforeAdvice beforeAdvice;

    private MethodAfterReturningAdvice afterReturningAdvice;

    public MethodAdviceInterceptor() {
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        if (beforeAdvice != null) {
            beforeAdvice.before(methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
        }
        Object ret = methodInvocation.proceed();
        if (afterReturningAdvice != null) {
            afterReturningAdvice.afterReturning(ret, methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
        }
        return ret;
    }

    public void setBeforeAdvice(MethodBeforeAdvice beforeAdvice) {
        this.beforeAdvice = beforeAdvice;
    }

    public MethodBeforeAdvice getBeforeAdvice() {
        return beforeAdvice;
    }

    public MethodAfterReturningAdvice getAfterReturningAdvice() {
        return afterReturningAdvice;
    }

    public void setAfterReturningAdvice(MethodAfterReturningAdvice afterReturningAdvice) {
        this.afterReturningAdvice = afterReturningAdvice;
    }

}
