package org.springframework.aop;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/4 14:34
 */
public class TargetSource {

    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    public Object getTarget() {
        return target;
    }

    public Class<?>[] getTargetClass() {
        return this.target.getClass().getInterfaces();
    }

}
