package org.springframework.context.event;

/**
 * 关闭容器时的事件
 *
 * @author chenJianhang
 * @version 1.0 2022/12/2 22:20
 */
public class ContextClosedEvent extends ApplicationContextEvent {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ContextClosedEvent(Object source) {
        super(source);
    }

}
