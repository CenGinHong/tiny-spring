package org.springframework.context.event;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author chenJianhang
 * @version 1.0 2022/12/2 22:23
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {
        for (ApplicationListener<ApplicationEvent> applicationListener : applicationListenerSet) {
            if (supportsEvent(applicationListener, event)) {
                // 如果是监听器所关心的事件，触发监听器内容
                applicationListener.onApplicationEvent(event);
            }
        }
    }

    /**
     * 监听器是否对该事件感兴趣
     *
     * @param applicationListener applicationListener
     * @param event               event
     * @return 是否对该事件感兴趣
     */
    protected boolean supportsEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent event) {
        // 获取这个类实现的接口的第一个参数化类型，就是ApplicationEvent<E extends  ApplicationEvent>
        Type type = applicationListener.getClass().getGenericInterfaces()[0];
        // 获得E
        Type actualTypeArg = ((ParameterizedType) type).getActualTypeArguments()[0];
        String className = actualTypeArg.getTypeName();
        Class<?> eventClassName;
        try {
            // 得到这个event的class的类名
            eventClassName = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("wrong event class name: " + className);
        }
        // 监听器监听的事件是否是这个事件或其子类
        return eventClassName.isAssignableFrom(event.getClass());
    }

}
