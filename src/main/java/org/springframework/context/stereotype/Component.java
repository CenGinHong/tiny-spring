package org.springframework.context.stereotype;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
// @Documented在生成javadoc文档时会附上
@Documented
public @interface Component {

    String value() default "";

}
