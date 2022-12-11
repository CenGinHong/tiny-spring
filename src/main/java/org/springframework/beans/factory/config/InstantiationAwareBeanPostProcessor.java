package org.springframework.beans.factory.config;

import org.springframework.beans.BeansException;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/10 21:08
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    /**
     * 在bean实例化之前执行，如果bean里有match该类的切面类，返回代理对象
     *
     * @param beanClass beanClass
     * @param beanName  beanName
     * @return 被替换的bean
     * @throws BeansException BeansException
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

}
