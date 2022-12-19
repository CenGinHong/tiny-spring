package org.springframework.core.convert.converter;

/**
 * 类型转换器注册接口
 *
 * @author chenJianhang
 * @version 1.0 2022/12/17 19:10
 */
public interface ConverterRegistry {

    void addConverter(Converter<?, ?> converter);

    void addConverterFactory(ConverterFactory<?, ?> converterFactory);

    void addConverter(GenericConverter converter);

}
