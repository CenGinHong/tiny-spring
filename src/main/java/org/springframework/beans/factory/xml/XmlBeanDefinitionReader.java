package org.springframework.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanReference;
import org.springframework.beans.factory.support.AbstractAutowireDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author chenJianhang
 * @version 1.0 2022/11/29 12:59
 */
public class XmlBeanDefinitionReader extends AbstractAutowireDefinitionReader {

    public static final String BEAN_ELEMENT = "bean";
    public static final String PROPERTY_ELEMENT = "property";
    public static final String ID_ATTRIBUTE = "id";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String REF_ATTRIBUTE = "ref";
    public static final String INIT_METHOD_ATTRIBUTE = "init-method";
    public static final String DESTROY_METHOD_ATTRIBUTE = "destroy-method";

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try (InputStream inputStream = resource.getInputStream()) {
            doLoadBeanDefinitions(inputStream);
        } catch (IOException | DocumentException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    protected void doLoadBeanDefinitions(InputStream inputStream) throws DocumentException {
        SAXReader reader = new SAXReader();
        org.dom4j.Document document = reader.read(inputStream);
        Element beans = document.getRootElement();
        List<Element> beanList = beans.elements(BEAN_ELEMENT);
        for (Element bean : beanList) {
            String idAttr = bean.attributeValue(ID_ATTRIBUTE);
            String beanNameAttr = bean.attributeValue(NAME_ATTRIBUTE);
            String classNameAttr = bean.attributeValue(CLASS_ATTRIBUTE);
            String initMethodNameAttr = bean.attributeValue(INIT_METHOD_ATTRIBUTE);
            String destroyMethodNameAttr = bean.attributeValue(DESTROY_METHOD_ATTRIBUTE);
            Class<?> clazz;
            try {
                clazz = Class.forName(classNameAttr);
            } catch (ClassNotFoundException e) {
                throw new BeansException("Cannot find class [" + classNameAttr + "]");
            }
            // id优先于name
            String beanName = StrUtil.isNotEmpty(idAttr) ? idAttr : beanNameAttr;
            if (StrUtil.isEmpty(beanName)) {
                // id和name都是空，将类名的第一个字母转小写作为bean的名称
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }
            // 不允许存在同名的bean
            if (getRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }

            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setInitMethodName(initMethodNameAttr);
            beanDefinition.setDestroyMethodName(destroyMethodNameAttr);
            List<Element> propertyList = bean.elements(PROPERTY_ELEMENT);
            for (Element property : propertyList) {
                // 解析property标签
                String propNameAttr = property.attributeValue(NAME_ATTRIBUTE);
                String propValueAttr = property.attributeValue(VALUE_ATTRIBUTE);
                String propRefAttr = property.attributeValue(REF_ATTRIBUTE);
                if (StrUtil.isEmpty(propNameAttr)) {
                    throw new BeansException("The name attribute of property of [" + beanName + "] cannot be null or empty");
                }
                Object value = propValueAttr;
                // 引用其他的bean，先置入beanRef
                if (StrUtil.isNotEmpty(propRefAttr)) {
                    value = new BeanReference(propRefAttr);
                }
                // 指入beanDef的prop列表
                PropertyValue propertyValue = new PropertyValue(propNameAttr, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }
            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
    }
}
