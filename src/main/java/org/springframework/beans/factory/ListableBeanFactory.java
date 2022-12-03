package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

import java.util.Map;

/**
 * 该接口规范list合条件的bean的方法
 *
 * @author chenJianhang
 * @version 1.0 2022/11/28 22:53
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * 返回所有指定类型的实例，如果没有创建先创建
     *
     * @param type type
     * @param <T>  T
     * @return 所有指定类型的实例
     * @throws BeansException BeansException
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    /**
     * 返回定义的所有bean的名称
     *
     * @return 所有bean的名称
     */
    String[] getBeanDefinitionNames();

}
