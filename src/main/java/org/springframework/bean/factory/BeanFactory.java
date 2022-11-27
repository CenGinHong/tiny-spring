package org.springframework.bean.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenJianhang
 * @version 1.0 2022/11/27 21:10
 */
public interface BeanFactory {
     Object getBean(String name);
}
