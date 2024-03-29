package org.springframework.beans.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * 该接口
 *
 * @author chenJianhang
 * @version 1.0 2022/11/28 22:57
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    /**
     * 根据名称查找BeanDefinition
     *
     * @param beanName beanName
     * @return bean
     * @throws BeansException BeansException 找不到时抛出
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 提前实例化所有单例实例
     *
     * @throws BeansException BeansException
     */
    void preInstantiateSingletons() throws BeansException;

}
