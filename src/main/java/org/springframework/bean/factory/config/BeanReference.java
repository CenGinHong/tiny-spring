package org.springframework.bean.factory.config;

/**
 * 包装bean对象，如果一个bean对象引用i另一个bean对象是用此包装
 *
 * @author chenJianhang
 * @version 1.0 2022/11/28 16:15
 */
public class BeanReference {

    private final String beanName;


    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }

}
