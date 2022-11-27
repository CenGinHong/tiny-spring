package org.springframework.bean.factory.support;

import org.springframework.bean.factory.BeanFactory;
import org.springframework.bean.BeansException;
import org.springframework.bean.factory.config.BeanDefinition;

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
        BeanDefinition beanDefinition = getBeanDefinition(name);
        // 创建bean细节方式的交给下游
        return createBean(name, beanDefinition);
    }

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

}
