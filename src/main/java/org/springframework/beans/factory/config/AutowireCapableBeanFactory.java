package org.springframework.beans.factory.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;

/**
 * 暂时直接继承beanFactory
 *
 * @author chenJianhang
 * @version 1.0 2022/11/28 23:00
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    /**
     * 执行BeanPostProcessors的postProcessBeforeInitialization方法
     *
     * @param existingBean 已经存在的bean
     * @param beanName beanName
     * @return 替换或修改的bean
     * @throws BeansException BeansException
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean,String beanName) throws BeansException;

    /**
     * 执行BeanPostProcessors的postProcessAfterInitialization方法
     *
     * @param existingBean 已经存在的bean
     * @param beanName beanName
     * @return 替换或修改的bean
     * @throws BeansException BeansException
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
            throws BeansException;

}
