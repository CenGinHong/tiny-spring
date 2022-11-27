package org.springframework.bean.factory.support;

import org.springframework.bean.factory.config.BeanDefinition;

/**
 * @author chenJianhang
 * @version 1.0 2022/11/27 21:56
 */
public interface BeanDefinitionRegistry {

    /**
     * 向注册表中注BeanDefinition
     * @param beanName beanName
     * @param beanDefinition beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

}
