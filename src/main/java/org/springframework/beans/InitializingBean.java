package org.springframework.beans;

/**
 * @author chenJianhang
 * @version 1.0 2022/11/30 19:50
 */
public interface InitializingBean {

    void afterPropertiesSet() throws Exception;

}
