package org.springframework.test.common;

import org.springframework.beans.factory.FactoryBean;

import java.util.HashSet;
import java.util.Set;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/19 12:26
 */
public class ConvertersFactoryBean implements FactoryBean<Set<?>> {

    @Override
    public Set<?> getObject() {
        Set<Object> converterMap = new HashSet<>();
        StringToLocalDateConverter stringToLocalDateConverter = new StringToLocalDateConverter("yyyy-MM-dd");
        converterMap.add(stringToLocalDateConverter);
        return converterMap;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
