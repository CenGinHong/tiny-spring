package org.springframework.beans;

/**
 * @author chenJianhang
 * @version 1.0 2022/11/27 21:34
 */
public class BeansException extends RuntimeException {

    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
