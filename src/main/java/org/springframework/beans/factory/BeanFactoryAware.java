package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/2 16:55
 */
public interface BeanFactoryAware extends Aware {

    void setBeanFactory(BeanFactory beanFactory) throws BeansException;

}
