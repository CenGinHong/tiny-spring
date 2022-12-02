package org.springframework.context.event;

/**
 * 容器刷新完成时的事件
 *
 * @author chenJianhang
 * @version 1.0 2022/12/2 22:22
 */
public class ContextRefreshedEvent extends ApplicationContextEvent {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ContextRefreshedEvent(Object source) {
        super(source);
    }

}
