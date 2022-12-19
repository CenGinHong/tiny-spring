package org.springframework.core.convert.converter;

/**
 * 类型转换工厂
 *
 * @author chenJianhang
 * @version 1.0 2022/12/17 19:08
 */
public interface ConverterFactory<S, R> {

    <T extends R> Converter<S, T> getConverter(Class<T> targetType);

}
