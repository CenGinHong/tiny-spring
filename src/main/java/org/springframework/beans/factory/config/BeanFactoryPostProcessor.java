package org.springframework.beans.factory.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ConfigurableListableBeanFactory;

/**
 * 允许在bean实例化之前修改bean的定义信息即BeanDefinition的信息,并非通过侵入代码，而是直接获取
 *
 * @author chenJianhang
 * @version 1.0 2022/11/29 17:03
 */
public interface BeanFactoryPostProcessor {

    /**
     * 在所有BeanDefinition加载完成后，但在bean实例化之前，提供修改BeanDefinition属性值的机制
     *
     * @param beanFactory beanFactory
     * @throws BeansException BeansException
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;

}
