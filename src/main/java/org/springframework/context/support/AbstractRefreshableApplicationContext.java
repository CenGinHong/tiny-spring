package org.springframework.context.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * 把DefaultListableBeanFactory作为成员变量持有容器
 * @author chenJianhang
 * @version 1.0 2022/11/29 21:20
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    private DefaultListableBeanFactory beanFactory;

    /**
     * 创建bean工厂
     *
     * @return DefaultListableBeanFactory
     */
    protected DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    /**
     * 加载beanDef
     *
     * @param factory factory
     */
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory factory);

    @Override
    protected void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory factory = createBeanFactory();
        loadBeanDefinitions(factory);
        this.beanFactory = factory;
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

}
