package org.springframework.bean.factory.support;

import org.springframework.bean.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * 定义添加和获取单例bean的方法
 * @author chenJianhang
 * @version 1.0 2022/11/27 21:27
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private final Map<String,Object> singletonObjects = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName,singletonObject);
    }

}
