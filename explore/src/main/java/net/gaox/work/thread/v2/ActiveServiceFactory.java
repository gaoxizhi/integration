package net.gaox.work.thread.v2;

import net.gaox.thread.future.Future;
import net.gaox.work.thread.base.ActiveFuture;
import net.gaox.work.thread.exception.IllegalActiveMethod;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <p> 异步消息工厂方法 </p>
 *
 * @author gaox·Eric
 * @date 2024-01-31 13:22
 */
public class ActiveServiceFactory {

    /**
     * 将 ActiveMessageQueue 定义成 static 保持其在整个 JVM 进程中是唯一的
     * ActiveDaemonThread 会在此刻启动
     */
    private final static ActiveMessageQueue ACTIVE_MESSAGE_QUEUE = new ActiveMessageQueue();

    /**
     * 生成 Service 的代理类
     *
     * @param instance Service 实例
     * @return Service 的代理类
     */
    public static <T> T active(T instance) {
        Object proxy = Proxy.newProxyInstance(instance.getClass().getClassLoader(),
                instance.getClass().getInterfaces(), new ActiveInvocationHandler<>(instance));
        return (T) proxy;
    }

    /**
     * <p> 生成 Service 的代理类 </p>
     *
     * @author gaox·Eric
     * @date 2024-01-31 15:12
     */
    private static class ActiveInvocationHandler<T> implements InvocationHandler {

        /**
         * Service 实例
         */
        private final T instance;

        ActiveInvocationHandler(T instance) {
            this.instance = instance;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            // 处理 ActiveMethod 注解的方法
            if (method.isAnnotationPresent(ActiveMethod.class)) {
                //check the method
                this.checkMethod(method);
                // 构建 消息的 实现类、方法、参数和 Future
                ActiveMessage.ActiveMessageBuilder builder = ActiveMessage.builder();
                builder.method(method).objects(args).service(instance);
                // 构建 Future 结果
                Object result = null;
                if (isReturnFutureType(method)) {
                    result = new ActiveFuture<>();
                    builder.future((ActiveFuture) result);
                }
                // 加入消息队列
                ACTIVE_MESSAGE_QUEUE.offer(builder.build());
                return result;
            }

            // 没有注解的，不特殊处理
            return method.invoke(instance, args);
        }

        /**
         * 检查方法是否符合要求
         * 只允许 void、Future 类型
         *
         * @param method 方法
         * @throws IllegalActiveMethod 方法不符合要求
         */
        private void checkMethod(Method method) throws IllegalActiveMethod {
            if (!isReturnVoidType(method) && !isReturnFutureType(method)) {
                throw new IllegalActiveMethod("the method [" + method.getName() + " return type must be void/Future");
            }
        }

        /**
         * 判断方法的返回值是否为 Future
         *
         * @param method 方法
         * @return 返回值是否为 Future
         */
        private boolean isReturnFutureType(Method method) {
            return method.getReturnType().isAssignableFrom(Future.class);
        }

        /**
         * 判断方法的返回值是否为 void
         *
         * @param method 方法
         * @return 返回值是否为 void
         */
        private boolean isReturnVoidType(Method method) {
            return method.getReturnType().equals(Void.TYPE);
        }
    }

}
