package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 继承了实现了 SingletonBeanRegistry 的DefaultSingletonBeanRegistry 类，具备了单例 Bean 的注册功能。
 * 存储了beanPostProcessorList，同时拥有DefaultSingletonBeanRegistry存储bean的能力
 * 实现了getBean方法
 *
 * @author chenJianhang
 * @version 1.0 2022/11/27 21:31
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

    private final Map<String, Object> factoryBeanObjectCache = new HashMap<>();

    @Override
    public Object getBean(String name) {
        // 从DefaultSingletonBeanRegistry 的map拿
        Object sharedInstance = getSingleton(name);
        if (sharedInstance == null) {
            // 获取和创建bean细节方式的交给下游
            BeanDefinition beanDefinition = getBeanDefinition(name);
            // 由实现此抽象类的其他类做相应实现
            sharedInstance = createBean(name, beanDefinition);

        }
        return getObjectForBeanInstance(sharedInstance, name);
    }

    /**
     * 如果是beanFactory类型，从FactoryBean#getObject中创建bean
     *
     * @param beanInstance beanInstance
     * @param beanName     beanName
     * @return Object
     */
    protected Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        Object object = beanInstance;
        // 如果是beanFactory类型
        if (beanInstance instanceof FactoryBean) {
            @SuppressWarnings("rawtypes") FactoryBean factoryBean = (FactoryBean) beanInstance;
            try {
                if (factoryBean.isSingleton()) {
                    // singleTon作用域bean，从缓存中获取
                    object = this.factoryBeanObjectCache.get(beanName);
                    if (object == null) {
                        object = factoryBean.getObject();
                        this.factoryBeanObjectCache.put(beanName, object);
                    }
                } else {
                    object = factoryBean.getObject();
                }
            } catch (Exception e) {
                throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
            }
        }
        return object;
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T> T getBean(String name, Class<T> requireType) {
        // 这里强转？不和类型呢
        return ((T) getBean(name));
    }

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        // 有则模式
        this.beanPostProcessorList.remove(beanPostProcessor);
        this.beanPostProcessorList.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessorList() {
        return beanPostProcessorList;
    }

}
