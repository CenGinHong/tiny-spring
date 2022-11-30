package org.springframework.beans.factory;

/**
 * 该接口规范bean的销毁方法
 *
 * @author chenJianhang
 * @version 1.0 2022/11/30 13:35
 */
public interface DisposableBean {

    void destroy() throws Exception;

}
