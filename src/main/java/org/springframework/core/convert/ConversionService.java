package org.springframework.core.convert;

/**
 * 类型转换抽象接口
 *
 * @author chenJianhang
 * @version 1.0 2022/12/17 19:22
 */
public interface ConversionService {

    boolean canConvert(Class<?> sourceType, Class<?> targetType);

    <T> T convert(Object source, Class<T> target);

}
