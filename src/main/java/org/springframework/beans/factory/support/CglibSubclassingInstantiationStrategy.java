package org.springframework.beans.factory.support;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;

/**
 * @author chenJianhang
 * @version 1.0 2022/11/27 22:48
 */
public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {
    /**
     * 使用CGLIB动态生成子类
     *
     * @param beanDefinition beanDefinition
     * @return bean实例
     * @throws BeansException BeansException
     */
    @Override
    public Object instantiate(BeanDefinition beanDefinition) throws BeansException {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        enhancer.setCallback((MethodInterceptor) (obj, method, argsTemp, proxy) -> proxy.invokeSuper(obj, argsTemp));
        return enhancer.create();
    }
}
