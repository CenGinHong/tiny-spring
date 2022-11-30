package org.springframework.beans.factory.config;

/**
 * 单例注册表,定义获取bean单例的方法
 *
 * @author chenJianhang
 * @version 1.0 2022/11/27 21:25
 */
public interface SingletonBeanRegistry {

    Object getSingleton(String beanName);

}
