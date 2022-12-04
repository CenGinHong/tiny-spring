package org.springframework.beans.factory.support;

import org.springframework.beans.factory.config.BeanDefinition;

/**
 * 主要是为了DefaultListableBeanFactory中的beanDef存取方法做铺垫
 *
 * @author chenJianhang
 * @version 1.0 2022/11/27 21:56
 */
public interface BeanDefinitionRegistry {

    /**
     * 向注册表中注BeanDefinition
     *
     * @param beanName       beanName
     * @param beanDefinition beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 根据名称查找BeanDefinition
     *
     * @param beanName beanName
     * @return BeanDefinition
     */
    BeanDefinition getBeanDefinition(String beanName);

    /**
     * 是否包含指定名称的BeanDefinition
     *
     * @param beanName beanName
     * @return BeanDefinition
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 返回定义的所有bean的名称
     *
     * @return 所有bean的名称
     */
    String[] getBeanDefinitionNames();
}
