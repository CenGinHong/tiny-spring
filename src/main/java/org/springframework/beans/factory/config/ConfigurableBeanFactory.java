package org.springframework.beans.factory.config;

import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.util.StringValueResolver;

/**
 * SingletonBeanRegistry 定义获取单例的方法
 * 该接口规范添加BeanPostProcessor和销毁实例方法
 *
 * @author chenJianhang
 * @version 1.0 2022/11/29 10:34
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    /**
     * @param beanPostProcessor beanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 销毁单例bean
     */
    void destroySingletons();

    /**
     * 往beanFactory加入 valueResolver
     *
     * @param valueResolver valueResolver
     */
    void addEmbeddedValueResolver(StringValueResolver valueResolver);

    /**
     * @param value
     * @return
     */
    String resolveEmbeddedValue(String value);

}
