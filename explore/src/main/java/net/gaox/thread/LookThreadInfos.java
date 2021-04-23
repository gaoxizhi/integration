package net.gaox.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p> 查看普通Java程序有哪些线程 </p>
 * 一个Java程序的运行是main线程和多个其他线程同时运行
 *
 * @author gaox·Eric
 * @date 2021/3/18 22:48
 */
public class LookThreadInfos {
    public static void main(String[] args) {
        // 获取 Java 线程管理 MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        // 不需要获取同步的 monitor 和 synchronizer 信息，仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);

        List<ThreadInfo> list = Arrays.asList(threadInfos);
        list.stream().sorted((o1, o2) -> (int) (o1.getThreadId() - o2.getThreadId()))
                .map(ThreadInfo::toString).forEach(System.out::println);

        List<ThreadInfo> collect = list.stream().sorted((o1, o2) -> (int) (o1.getThreadId() - o2.getThreadId()))
                .collect(Collectors.toList());
        // 遍历线程信息，仅打印线程 ID 和线程名称信息
        for (ThreadInfo threadInfo : collect) {
            System.out.println("[" + threadInfo.getThreadId() + "] " + threadInfo.getThreadName());
        }
    }
}
