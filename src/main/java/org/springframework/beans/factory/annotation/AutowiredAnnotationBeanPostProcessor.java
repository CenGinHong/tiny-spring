package org.springframework.beans.factory.annotation;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

import java.lang.reflect.Field;

/**
 * 处理注册bean的
 *
 * @author chenJianhang
 * @version 1.0 2022/12/14 17:53
 */
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        // 处理@Value注解
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Value valueAnnotation = field.getAnnotation(Value.class);
            if (valueAnnotation != null) {
                String value = valueAnnotation.value();
                // 替换其中的占位符
                value = beanFactory.resolveEmbeddedValue(value);
                // 注入到字段
                BeanUtil.setFieldValue(bean, field.getName(), value);
            }
        }

        for (Field field : fields) {
            // 获取每一个有autowired的注解
            Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
            // 如果不为空
            if (autowiredAnnotation != null) {
                // 获取注解字段的类型
                Class<?> fieldType = field.getType();
                String dependentBeanName;
                Qualifier qualifierAnnotation = field.getAnnotation(Qualifier.class);
                Object dependentBean;
                if (qualifierAnnotation != null) {
                    dependentBeanName = qualifierAnnotation.value();
                    // 用名字和类型一起找
                    dependentBean = beanFactory.getBean(dependentBeanName, fieldType);
                } else {
                    // 只用类型找
                    dependentBean = beanFactory.getBean(fieldType);
                }
                BeanUtil.setFieldValue(bean, field.getName(), dependentBean);
            }
        }
        return pvs;
    }
}
