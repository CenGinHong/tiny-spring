package org.springframework.context;

import java.util.EventListener;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/2 22:03
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    void onApplicationEvent(E event);

}
