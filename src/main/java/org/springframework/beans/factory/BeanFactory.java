package org.springframework.beans.factory;

/**
 * @author chenJianhang
 * @version 1.0 2022/11/27 21:10
 */
public interface BeanFactory {
    /**
     * 获取bean
     *
     * @param name name
     * @return bean
     */
    Object getBean(String name);

    /**
     * 根据名称和类型查找bean
     *
     * @param name        名称
     * @param requireType 类型
     * @param <T>         T
     * @return bean
     */
    <T> T getBean(String name, Class<T> requireType);

}
