package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 存储了beanPostProcessorList，同时拥有DefaultSingletonBeanRegistry存储bean的能力
 * 实现了getBean方法
 *
 * @author chenJianhang
 * @version 1.0 2022/11/27 21:31
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

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

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        // 有则模式
        this.beanPostProcessorList.remove(beanPostProcessor);
        this.beanPostProcessorList.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessorList() {
        return beanPostProcessorList;
    }

}
