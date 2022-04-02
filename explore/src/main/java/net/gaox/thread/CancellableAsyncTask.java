package net.gaox.thread;

import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.*;

/**
 * <p> 可取消的异步任务 </p>
 *
 * @author gaox·Eric
 * @date 2020/3/9 12:37
 */
public class CancellableAsyncTask {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        FutureTask future = new FutureTask(() -> {
            // 真正的任务在这里执行，这里返回类型为string，可以为任意类型的数据
            URL realUrl = new URL("http://www.baidu.com");
            //打开和url之间的链接
            URLConnection connection = realUrl.openConnection();
            connection.connect();
            return connection;
        });
        executor.execute(future);
        try {
            URLConnection result = (URLConnection) future.get(5000, TimeUnit.MILLISECONDS);
            System.out.println(result.getHeaderFields());
        } catch (InterruptedException e) {
            future.cancel(true);
            System.out.print("提前中断");
            e.printStackTrace();
        } catch (ExecutionException e) {
            future.cancel(true);
            System.out.print("任务奔溃");
            e.printStackTrace();
        } catch (TimeoutException e) {
            future.cancel(true);
            System.out.print("超时异常");
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

}