package net.gaox.message.event.base;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * <p> 注册表 </p>
 * 维护 subscriber 与 topic 的映射关系
 *
 * @author gaox·Eric
 * @date 2024-02-01 10:21
 */
class Registry {

    /**
     * 存储 subscriber 集合和 topic 之间关系的 map
     */
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<Subscriber>> subscriberContainer =
            new ConcurrentHashMap<>();

    /**
     * 获取 subscriber Object 的方法集合然后进行绑定
     *
     * @param subscriber subscriber
     */
    public void bind(Object subscriber) {
        List<Method> subscribeMethods = getSubscribeMethods(subscriber);
        subscribeMethods.forEach(m -> tierSubscriber(subscriber, m));
    }

    /**
     * 解绑指定的 subscriber
     *
     * @param subscriber subscriber
     */
    public void unbind(Object subscriber) {
        // 为了提高速度，只对指定 subscriber 进行失效操作
        subscriberContainer.values().parallelStream()
                .flatMap(Collection::stream)
                .filter(subscriber::equals)
                .forEach(s -> s.setDisable(true));
    }

    public ConcurrentLinkedQueue<Subscriber> scanSubscriber(final String topic) {
        return subscriberContainer.get(topic);
    }

    /**
     * 获取当前类和父类的所有 Subscribe 注解的方法
     *
     * @param subscriber subscriber
     * @return list
     */
    private List<Method> getSubscribeMethods(Object subscriber) {
        final List<Method> methods = new ArrayList<>();
        Class<?> temp = subscriber.getClass();
        // 不断获取当前类和父类的所有 Subscribe 注解的方法
        while (temp != null) {
            // 获取所有的方法
            Method[] declaredMethods = temp.getDeclaredMethods();
            // 符合的方法，添加到 methods 中
            // Subscribe 注解的的方法 && 只有一个入参 && 是 public 方法
            Arrays.stream(declaredMethods)
                    .filter(m -> m.isAnnotationPresent(Subscribe.class))
                    .filter(m -> 1 == m.getParameterCount())
                    .filter(m -> Modifier.PUBLIC == m.getModifiers())
                    .forEach(methods::add);
            // 继续扫描父类
            temp = temp.getSuperclass();
        }
        return methods;
    }

    private void tierSubscriber(Object subscriber, Method method) {
        final Subscribe subscribe = method.getDeclaredAnnotation(Subscribe.class);
        String topic = subscribe.topic();
        // 当某 topic 没有 subscriberQueue 的时候创建一个
        subscriberContainer.computeIfAbsent(topic, key -> new ConcurrentLinkedQueue<>());
        // 创建一个 subscriber 并且加入 subscriber 列表中
        subscriberContainer.get(topic).add(new Subscriber(subscriber, method));
    }

}
