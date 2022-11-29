package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;

/**
 * @author chenJianhang
 * @version 1.0 2022/11/27 21:31
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String name) {
        // 从DefaultSingletonBeanRegistry 的map拿
        Object bean = getSingleton(name);
        if (bean != null) {
            return bean;
        }
        // 获取和创建bean细节方式的交给下游
        BeanDefinition beanDefinition = getBeanDefinition(name);
        return createBean(name, beanDefinition);
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T> T getBean(String name, Class<T> requireType) {
        // 这里强转？不和类型呢
        return ((T) getBean(name));
    }

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

}
