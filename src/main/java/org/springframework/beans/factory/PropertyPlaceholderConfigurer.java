package org.springframework.beans.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/11 21:08
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    public static final String PLACEHOLDER_PREFIX = "${";

    public static final String PLACEHOLDER_SUFFIX = "}";

    private String location;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 加载属性配置文件
        Properties properties = loadProperties();

        // 属性只替换占位符
        processProperties(beanFactory, properties);
    }

    /**
     * 加载配置文件
     *
     * @return 配置文件
     */
    private Properties loadProperties() {
        try {
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(location);
            Properties properties = new Properties();
            properties.load(resource.getInputStream());
            return properties;
        } catch (IOException e) {
            throw new BeansException("Could not load properties [" + location + "] ", e);
        }
    }

    private void processProperties(ConfigurableListableBeanFactory beanFactory, Properties properties) {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanName : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            resolvePropertyValues(beanDefinition, properties);
        }
    }

    private void resolvePropertyValues(BeanDefinition beanDefinition, Properties properties) {
        // 在xml定义的所有property
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
            Object value = propertyValue.getValue();
            if (value instanceof String) {
                // 仅支持简单的占位符格式
                String strVal = (String) value;
                StringBuilder sb = new StringBuilder(strVal);
                int startIdx = strVal.indexOf(PLACEHOLDER_PREFIX);
                int endIdx = strVal.indexOf(PLACEHOLDER_SUFFIX);
                if (startIdx != -1 && endIdx != -1 && startIdx < endIdx) {
                    // 把配置文件的key抽取
                    String propKey = strVal.substring(startIdx + 2, endIdx);
                    String propVal = properties.getProperty(propKey);
                    // 把${xxx}在配置文件找到xxx=yy,替换
                    sb.replace(startIdx, endIdx + 1, propVal);
                    // 原来带有替代符的没有被删除，但是会被后续的覆盖掉
                    propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), sb.toString()));
                }
            }
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
