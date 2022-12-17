package org.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.InitializingBean;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.PropertyValue;
import org.springframework.beans.factory.PropertyValues;
import org.springframework.beans.factory.config.*;

import java.lang.reflect.Method;

/**
 * 集中了大部分功能，例如AbstractBeanFactory的存储bean对象，postProcessor对象等
 * AutowireCapableBeanFactory接口定义了应用postProcessor来修改bean的方法
 *
 * @author chenJianhang
 * @version 1.0 2022/11/27 21:51
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        // 如果bean需要代理，则返回代理对象
        // 代理bean对象不是代理的，也不会执行initializeBean那些方法
        Object bean = resolveBeforeInstantiation(beanName, beanDefinition);
        if (bean != null) {
            return bean;
        }
        return doCreateBean(beanName, beanDefinition);
    }

    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition) {
        Object bean;
        try {
            // 根据实例化策略构造对象
            bean = createBeanInstance(beanDefinition);
            boolean continueWithPropertyPopulation = applyBeanPostProcessorsAfterInstantiation(beanName, bean);
            if (!continueWithPropertyPopulation) {
                return bean;
            }
            // 在设置bean属性之前，允许applyBeanPostProcessorsBeforeApplyingPropertyValues
            // TODO 为什么不放在invokeBeanFactoryPostProcessors里做beanDef的替换，要创建完实例之后再去替换
            // 目前推猜，写在xml的bean的${}用这里beanFactoryPostProcess配置，用@Value的直接beanUtil.setField
            applyBeanPostProcessorsBeforeApplyingPropertyValues(beanName, bean, beanDefinition);
            // 为bean填入参数
            applyPropertyValues(beanName, bean, beanDefinition);
            // 初始化该bean，调用前后修改的参数值
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }
        // 注册有销毁方法的bean
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);
        // 将bean对象放入容器，这里放入的是DefaultSingletonBeanRegistry的map
        if (beanDefinition.isSingleton()) {
            addSingleton(beanName, bean);
        }
        return bean;
    }

    private boolean applyBeanPostProcessorsAfterInstantiation(String beanName, Object bean) {
        boolean continueWithPropertyPopulation = true;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessorList()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                if (!((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessAfterInstantiation(bean, beanName)) {
                    continueWithPropertyPopulation = false;
                    break;
                }
            }
        }
        return continueWithPropertyPopulation;
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition) {
        return getInstantiationStrategy().instantiate(beanDefinition);
    }

    /**
     * 执行InstantiationAwareBeanPostProcessor的方法，如果bean需要代理，直接返回代理对象
     *
     * @param beanName       beanName
     * @param beanDefinition beanDefinition
     * @return Object
     */
    protected Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) {
        Object bean = applyBeanPostProcessorsBeforeInstantiation(beanDefinition.getBeanClass(), beanName);
        // 如果已经构成实例化，就Initialization
        if (bean != null) {
            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        }
        return bean;
    }

    /**
     * 在设置bean属性之前，允许BeanPostProcess修改属性值,
     * // TODO 为什么不放在invokeBeanFactoryPostProcessors(beanFactory);里做
     *
     * @param beanName       beanName
     * @param bean           bean
     * @param beanDefinition beanDefinition
     */
    protected void applyBeanPostProcessorsBeforeApplyingPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessorList()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                // 交给InstantiationAwareBeanPostProcessor进行属性替换
                PropertyValues pvs = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessPropertyValues(beanDefinition.getPropertyValues(), bean, beanName);
                if (pvs != null) {
                    for (PropertyValue propertyValue : pvs.getPropertyValues()) {
                        beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
                    }
                }
            }
        }
    }

    /**
     * 初始化之前实现，返回null将会形成短路
     *
     * @param beanClass beanClass
     * @param beanName  beanName
     * @return bean
     */
    protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessorList()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                // 调用DefaultAdvisorAutoProxyCreator，这个是在xml注入的bean
                // 如果这里返回null将会形成短路
                Object result = ((InstantiationAwareBeanPostProcessor) beanPostProcessor)
                        .postProcessBeforeInstantiation(beanClass, beanName);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    /**
     * 将beanDefinition存的构造用的参数注入
     *
     * @param beanName       beanName
     * @param bean           bean
     * @param beanDefinition beanDefinition
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            for (PropertyValue pv : beanDefinition.getPropertyValues().getPropertyValues()) {
                String name = pv.getName();
                Object value = pv.getValue();
                if (value instanceof BeanReference) {
                    BeanReference beanReference = (BeanReference) value;
                    // 如不存在，先去实例化其该bean对象
                    value = getBean(beanReference.getBeanName());
                }
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values for bean: " + beanName, e);
        }
    }

    protected Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }

        // 执行BeanPostProcessor的前置处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Invocation of init method of bean [" + beanName + "] failed", e);
        }
        // 代理类可能在这里生成
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        return wrappedBean;
    }

    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        if (beanDefinition.isSingleton()) {
            if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
                registryDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
            }
        }

    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object res = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessorList()) {
            Object cur = processor.postProcessBeforeInitialization(res, beanName);
            if (cur == null) {
                return res;
            }
            res = cur;
        }
        return res;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object res = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessorList()) {
            Object cur = processor.postProcessAfterInitialization(res, beanName);
            if (cur == null) {
                return res;
            }
            res = cur;
        }
        return res;
    }

    /**
     * 执行初始化方法，先执行afterPropertiesSet后执行xml的init-method
     *
     * @param beanName       beanName
     * @param bean           bean
     * @param beanDefinition beanDefinition
     * @throws Exception Exception
     */
    protected void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }
        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotEmpty(initMethodName)) {
            Method initMethod = ClassUtil.getPublicMethod(beanDefinition.getBeanClass(), initMethodName);
            if (initMethod == null) {
                throw new BeansException("Could not find an init method named [" + initMethodName + "] on bean with name [" + beanName + "]");
            }
            initMethod.invoke(bean);
        }
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

}
