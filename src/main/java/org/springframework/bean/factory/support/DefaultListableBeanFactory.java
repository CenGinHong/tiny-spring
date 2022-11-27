package org.springframework.bean.factory.support;

import org.springframework.bean.BeansException;
import org.springframework.bean.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenJianhang
 * @version 1.0 2022/11/27 21:57
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {

    /**
     * 这里只是存放BeanDefinition，真正实例化的对象在DefaultSingletonBeanRegistry的map中
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    @Override
    protected BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new BeansException("No bean named '" + beanName + "' is defined");
        }
        return beanDefinition;
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName,beanDefinition);
    }

}
