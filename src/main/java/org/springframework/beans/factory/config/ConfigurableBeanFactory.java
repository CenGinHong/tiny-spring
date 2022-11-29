package org.springframework.beans.factory.config;

import org.springframework.beans.factory.HierarchicalBeanFactory;

/**
 * SingletonBeanRegistry 定义获取单例的方法
 * HierarchicalBeanFactory 定义获取bean的方法，包括按类型和名字
 *
 * @author chenJianhang
 * @version 1.0 2022/11/29 10:34
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {
}
