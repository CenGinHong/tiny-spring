package org.springframework.core.io;

/**
 * 资源加载器接口
 *
 * @author chenJianhang
 * @version 1.0 2022/11/28 16:50
 */
public interface ResourceLoader {

    Resource getResource(String location);

}
