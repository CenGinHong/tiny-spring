package org.springframework.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/16 17:09
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER,
        ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Inherited
@Documented
public @interface Qualifier {

    String value() default "";

}
