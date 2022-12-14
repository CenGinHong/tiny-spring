package org.springframework.context.annotation;

import cn.hutool.core.util.ClassUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/13 19:17
 */
public class ClassPathScanningCandidateComponentProvider {

    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        // 扫描有org.springframework.stereotype.Component注解的类
        Set<Class<?>> classSet = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classSet) {
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            candidates.add(beanDefinition);
        }
        return candidates;
    }

}
