package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现和获取单例bean的方法，bean对象被存储在这里的map
 * 以及disposableBean也被存储在这里
 * <br/>
 * Bean在这三个缓存之间的流转顺序为（存在循环引用）：
 * <br/>
 * 通过反射创建Bean实例。是单例Bean，并且IoC容器允许Bean之间循环引用，保存到三级缓存中。
 * <br/>
 * 当发生了循环引用时，从三级缓存中取出Bean对应的ObjectFactory实例，调用其getObject方法，来获取早期曝光Bean，从三级缓存中移除，保存到二级缓存中。
 * <br/>
 * Bean初始化完成，生命周期的相关方法执行完毕，保存到一级缓存中，从二级缓存以及三级缓存中移除。
 * <br/>
 * Bean在这三个缓存之间的流转顺序为（没有循环引用）：
 * <br/>
 * 通过反射创建Bean实例。是单例Bean，并且IoC容器允许Bean之间循环引用，保存到三级缓存中。
 * <br/>
 * Bean初始化完成，生命周期的相关方法执行完毕，保存到一级缓存中，从二级缓存以及三级缓存中移除。
 *
 * @author chenJianhang
 * @version 1.0 2022/11/27 21:27
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * 一级缓存
     */
    private final Map<String, Object> singletonObjectMap = new HashMap<>();

    private final Map<String, DisposableBean> disposableBeanMap = new HashMap<>();

    /**
     * 三级缓存
     */
    private final Map<String, ObjectFactory<?>> singletonFactoryMap = new HashMap<>();
    /**
     * 二级缓存，存放为完全实例化完毕的bean的引用
     * 一般只有处于循环引用状态的Bean才会被保存在该缓存中。保存在该缓存中的Bean所实现Aware子接口的方法还未回调，
     * 自定义初始化方法未执行，也未经过BeanPostProcessor实现类的postProcessorBeforeInitialization、postProcessorAfterInitialization方法处理。
     * 如果启用了Spring AOP，并且处于切点表达式处理范围之内，那么会被增强，即创建其代理对象。
     * 普通Bean被增强(JDK动态代理或CGLIB)的时机是在AbstractAutoProxyCreator实现的BeanPostProcessor的postProcessorAfterInitialization方法中，
     * 而处于循环引用状态的Bean被增强的时机是在AbstractAutoProxyCreator实现的SmartInstantiationAwareBeanPostProcessor的getEarlyBeanReference方法中。
     */
    protected Map<String, Object> earlySingletonObjectMap = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        // 从一级缓存取
        Object singletonObject = singletonObjectMap.get(beanName);
        if (singletonObject == null) {
            // 从二级缓存取
            singletonObject = earlySingletonObjectMap.get(beanName);
            if (singletonObject == null) {
                ObjectFactory<?> singletonFactory = singletonFactoryMap.get(beanName);
                if (singletonFactory != null) {
                    // 获取工厂并创建bean，此时获取的可能是切面类
                    singletonObject = singletonFactory.getObject();
                    // bean被创造出来后，就从三级缓存放进二级缓存
                    earlySingletonObjectMap.put(beanName, singletonObject);
                    singletonFactoryMap.remove(beanName);
                }
            }
        }
        return singletonObject;
    }

    @Override
    public void addSingleton(String beanName, Object singletonObject) {
        singletonObjectMap.put(beanName, singletonObject);
        earlySingletonObjectMap.remove(beanName);
        singletonFactoryMap.remove(beanName);
    }

    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
        singletonFactoryMap.put(beanName, singletonFactory);
    }

    public void registryDisposableBean(String beanName, DisposableBean bean) {
        disposableBeanMap.put(beanName, bean);
    }

    public void destroySingletons() {
        List<String> beanNames = new ArrayList<>(disposableBeanMap.keySet());
        for (String beanName : beanNames) {
            DisposableBean disposableBean = disposableBeanMap.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name [" + beanName + "] threw an exception", e);
            }
        }
    }

}
