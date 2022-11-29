package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * 从资源文件读取BeanDefinition，不再硬编码
 *
 * @author chenJianhang
 * @version 1.0 2022/11/29 10:41
 */
public interface BeanDefinitionReader {

    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    /**
     * 读取资源文件（暂时是xml）,并解析成beanDef置入map
     *
     * @param resource resource
     * @throws BeansException BeansException
     */
    void loadBeanDefinitions(Resource resource) throws BeansException;

    /**
     * 读入location的resource并交给重载方法
     *
     * @param location location
     * @throws BeansException BeansException
     */
    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(String[] locations) throws BeansException;

}
