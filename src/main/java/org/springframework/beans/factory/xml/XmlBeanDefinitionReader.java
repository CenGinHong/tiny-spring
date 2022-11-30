package org.springframework.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanReference;
import org.springframework.beans.factory.support.AbstractAutowireDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

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
        } catch (IOException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    protected void doLoadBeanDefinitions(InputStream inputStream) {
        Document document = XmlUtil.readXML(inputStream);
        Element root = document.getDocumentElement();
        NodeList childNodes = root.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i) instanceof Element) {
                if (BEAN_ELEMENT.equals(childNodes.item(i).getNodeName())) {
                    Element bean = (Element) childNodes.item(i);
                    String idAttr = bean.getAttribute(ID_ATTRIBUTE);
                    String beanNameAttr = bean.getAttribute(NAME_ATTRIBUTE);
                    String classNameAttr = bean.getAttribute(CLASS_ATTRIBUTE);
                    String initMethodNameAttr = bean.getAttribute(INIT_METHOD_ATTRIBUTE);
                    String destroyMethodNameAttr = bean.getAttribute(DESTROY_METHOD_ATTRIBUTE);
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

                    // 读取对应的property
                    for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
                        if (bean.getChildNodes().item(j) instanceof Element) {
                            if (PROPERTY_ELEMENT.equals(bean.getChildNodes().item(j).getNodeName())) {
                                // 解析property标签
                                Element property = (Element) bean.getChildNodes().item(j);
                                String propNameAttr = property.getAttribute(NAME_ATTRIBUTE);
                                String propValueAttr = property.getAttribute(VALUE_ATTRIBUTE);
                                String propRefAttr = property.getAttribute(REF_ATTRIBUTE);
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
                        }
                    }
                    // 注册beanDef
                    getRegistry().registerBeanDefinition(beanName, beanDefinition);
                }
            }
        }
    }

}
