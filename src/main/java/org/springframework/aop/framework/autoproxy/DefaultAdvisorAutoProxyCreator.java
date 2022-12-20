package org.springframework.aop.framework.autoproxy;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.*;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/10 21:08
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private final Set<Object> earlyProxyReferenceSet = new HashSet<>();
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

    /**
     * 切面织入形成代理类
     *
     * @param bean     bean实例
     * @param beanName beanName
     * @return bean
     * @throws BeansException BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 如果缓存里没有
        if (!earlyProxyReferenceSet.contains(beanName)) {
            return wrapIfNecessary(bean, beanName);
        }
        return bean;
    }

    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        // 实例化后bean放入，这里是原始的bean
        earlyProxyReferenceSet.add(bean);
        // 这里返回的是动态代理的bean
        return wrapIfNecessary(bean, beanName);
    }

    protected Object wrapIfNecessary(Object bean, String beanName) {
        // 避免死循环
        if (isInfrastructureClass(bean.getClass())) {
            return bean;
        }
        // 从beanFactory注入AspectJExpressionPointcutAdvisor并获取
        // AspectJExpressionPointcutAdvisor就是那些切面类，封装了切面匹配和被切的要织入的前后置advice
        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();
        try {
            for (AspectJExpressionPointcutAdvisor advisor : advisors) {
                ClassFilter classFilter = advisor.getPointcut().getClassFilter();
                // 和这个类匹配
                if (classFilter.matches(bean.getClass())) {
                    AdvisedSupport adviceSupport = new AdvisedSupport();
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
        return bean;
    }


    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        return pvs;
    }
}
