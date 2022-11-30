package org.springframework.beans.factory.config;

import org.springframework.beans.BeansException;

/**
 * BeanPostProcessor在bean实例化后修改bean或替换bean
 *
 * @author chenJianhang
 * @version 1.0 2022/11/29 17:05
 */
public interface BeanPostProcessor {

    /**
     * 在bean执行初始化方法之前执行
     *
     * @param bean     bean实例
     * @param beanName beanName
     * @return bean
     * @throws BeansException BeansException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;


    /**
     * 在bean执行初始化方法之后执行此方法
     *
     * @param bean     bean实例
     * @param beanName beanName
     * @return bean
     * @throws BeansException BeansException
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

}
