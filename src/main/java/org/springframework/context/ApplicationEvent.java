package org.springframework.context;

import java.util.EventObject;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/2 21:27
 */
public abstract class ApplicationEvent extends EventObject {


    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationEvent(Object source) {
        super(source);
    }

}
