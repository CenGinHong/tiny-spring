package org.springframework.beans.factory;

/**
 * 当向容器获取该bean时，容器不是返回其本身，而是返回其FactoryBean#getObject方法的返回值
 *
 * @author chenJianhang
 * @version 1.0 2022/12/2 20:13
 */
public interface FactoryBean<T> {

    T getObject() throws Exception;

    boolean isSingleton();

}
