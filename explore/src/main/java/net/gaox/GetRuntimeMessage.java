package net.gaox;

import java.time.LocalDateTime;

/**
 * <p> 获取JVM运行程序的信息 </p>
 *
 * @author gaox·Eric
 * @date 2021/3/17 14:10
 */
public class GetRuntimeMessage {
    public static void main(String[] args) {
        // 获取Runtime类对象
        Runtime runtime = Runtime.getRuntime();

        String message = String.format("运行对象的状态：maxMemory=[%10d]; totalMemory=[%10d]; freeMemory=[%10d]",
                runtime.maxMemory(), runtime.totalMemory(), runtime.freeMemory());
        System.out.println("开始时刻：" + LocalDateTime.now());
        System.out.println(message);
        // VM参数：
        // -Xms10M -Xmx10M -Xmn2M -XX:SurvivorRatio=8 -XX:+HeapDumpOnOutOfMemoryError \
        // -XX:HeapDumpPath=/Users/gaox/data/gc.hprof -XX:+PrintGCDetails -Xloggc:/Users/gaox/data/gc.log
//        byte[] bytes = new byte[10 * 1024 * 1024];

        String str = "";
        for (int i = 0; i < 9_000; i++) {
            str += "";
        }
        str = null;

        message = String.format("运行对象的状态：maxMemory=[%10d]; totalMemory=[%10d]; freeMemory=[%10d]",
                runtime.maxMemory(), runtime.totalMemory(), runtime.freeMemory());
        System.out.println("执行String拼接后时刻：" + LocalDateTime.now());
        System.out.println(message);

        runtime.gc();

        message = String.format("运行对象的状态：maxMemory=[%10d]; totalMemory=[%10d]; freeMemory=[%10d]",
                runtime.maxMemory(), runtime.totalMemory(), runtime.freeMemory());
        System.out.println("执行gc后时刻：" + LocalDateTime.now());
        System.out.println(message);

    }
}
