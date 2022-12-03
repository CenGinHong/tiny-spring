package org.springframework.test.common.event;

import org.springframework.context.ApplicationListener;

/**
 * @author chenJianhang
 * @version 1.0 2022/11/29 19:57
 */
public class CustomEventListener implements ApplicationListener<CustomEvent> {

    @Override
    public void onApplicationEvent(CustomEvent event) {
        System.out.println(this.getClass().getName());
    }
}
