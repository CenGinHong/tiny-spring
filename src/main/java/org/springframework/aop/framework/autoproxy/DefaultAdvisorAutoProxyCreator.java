package org.springframework.aop.framework.autoproxy;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.*;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.util.Collection;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/10 21:08
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    /**
     * 是否是Advice，Pointcut，Advisor其中之一的子类
     *
     * @param beanClass beanClass
     * @return 是否是Advice，Pointcut，Advisor其中之一的子类
     */
    private boolean isInfrastructureClass(Class<?> beanClass) {
        // isAssignableFrom()方法是判断是否为某个类的父类，用于类
        // instanceof关键字是判断是否某个类的子类,用于实例
        return Advice.class.isAssignableFrom(beanClass)
                || Pointcut.class.isAssignableFrom(beanClass)
                || Advisor.class.isAssignableFrom(beanClass);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        // 避免死循环
        if (isInfrastructureClass(beanClass)) {
            return null;
        }
        // 从beanFactory注入AspectJExpressionPointcutAdvisor并获取
        // AspectJExpressionPointcutAdvisor就是那些切面类，封装了切面匹配和被切的要织入的前后置advice
        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();
        try {
            for (AspectJExpressionPointcutAdvisor advisor : advisors) {
                ClassFilter classFilter = advisor.getPointcut().getClassFilter();
                // 和这个类匹配
                if (classFilter.matches(beanClass)) {
                    AdvisedSupport adviceSupport = new AdvisedSupport();
                    BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                    // 反射构造对象
                    Object bean = beanFactory.getInstantiationStrategy().instantiate(beanDefinition);
                    TargetSource targetSource = new TargetSource(bean);
                    adviceSupport.setTargetSource(targetSource);
                    // MethodInterceptor 本身是advice的子类
                    adviceSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
                    adviceSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
                    return new ProxyFactory(adviceSupport).getProxy();
                }
            }
        } catch (Exception ex) {
            throw new BeansException("Error create proxy bean for: " + beanName, ex);
        }
        return null;
    }
}
