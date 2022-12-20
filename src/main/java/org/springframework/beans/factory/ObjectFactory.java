package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/19 21:35
 */
public interface ObjectFactory<T> {

    T getObject() throws BeansException;

}
