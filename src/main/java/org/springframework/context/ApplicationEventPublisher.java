package org.springframework.context;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/2 22:12
 */
public interface ApplicationEventPublisher {

    /**
     * 发布事件
     *
     * @param event 事件
     */
    void publishEvent(ApplicationEvent event);

}
