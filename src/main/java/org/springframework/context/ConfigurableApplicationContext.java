package org.springframework.context;

import org.springframework.beans.BeansException;

/**
 * @author chenJianhang
 * @version 1.0 2022/11/29 20:49
 */
public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * 刷新容器
     *
     * @throws BeansException BeansException
     */
    void refresh() throws BeansException;
}
