package org.springframework.bean.factory.support;

import org.springframework.bean.BeansException;
import org.springframework.bean.factory.config.BeanDefinition;

/**
 * @author chenJianhang
 * @version 1.0 2022/11/27 21:51
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        return doCreateBean(beanName, beanDefinition);
    }

    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Object bean;
        try {
            // 反射构造bean对象
            bean = beanClass.newInstance();
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }
        // 将bean对象放入容器，这里放入的是DefaultSingletonBeanRegistry的map
        addSingleton(beanName, bean);
        return bean;
    }

}
