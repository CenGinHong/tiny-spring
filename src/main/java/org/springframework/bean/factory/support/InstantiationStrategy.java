package org.springframework.bean.factory.support;

import org.springframework.bean.BeansException;
import org.springframework.bean.factory.config.BeanDefinition;

/**
 * Bean的实例化策略
 *
 * @author chenJianhang
 * @version 1.0 2022/11/27 22:39
 */
public interface InstantiationStrategy {

    Object instantiate(BeanDefinition beanDefinition) throws BeansException;
}
