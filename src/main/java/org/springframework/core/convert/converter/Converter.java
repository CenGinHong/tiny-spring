package org.springframework.core.convert.converter;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/17 19:07
 */
public interface Converter<S, T> {

    /**
     * 类型转换
     *
     * @param source 源对象
     * @return 转换后的对象
     */
    T convert(S source);
}
