package org.springframework.beans.factory.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.PropertyValues;

/**
 * 实例化之前执行
 *
 * @author chenJianhang
 * @version 1.0 2022/12/10 21:08
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    /**
     * 在bean实例化之前执行
     *
     * @param beanClass beanClass
     * @param beanName  beanName
     * @return 被替换的bean
     * @throws BeansException BeansException
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    /**
     * 在bean实例化之后，设置属性之前执行
     *
     * @param bean     bean
     * @param beanName beanName
     * @return boolean
     * @throws BeansException BeansException
     */
    boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException;

    PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException;

    default Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
