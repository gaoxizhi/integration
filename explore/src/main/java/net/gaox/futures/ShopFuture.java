package net.gaox.futures;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static java.util.stream.Collectors.toList;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2022/8/7 02:22
 */
public class ShopFuture {
    public static void main(String[] args) {
        ShopFuture shopFuture = new ShopFuture();

        // 模拟多条记录，超过核心数量
        List<Product> products = Arrays.asList(
                new Product("one"),
                new Product("two"),
                new Product("three"),
                new Product("four"),
                new Product("five"),
                new Product("six"),
                new Product("seven"),
                new Product("eight"),
                new Product("nine"),
                new Product("ten"),
                new Product("eleven"),
                new Product("twelve"),
                new Product("thirteen"),
                new Product("fourteen"),
                new Product("fifteen"),
                new Product("sixteen"),
                new Product("seventeen"),
                new Product("eighteen")
        );

        System.out.println("do test 1");
        shopFuture.test1("iPhone13 pro max");
        System.out.println("do test 2");
        shopFuture.test2("iPhone12 pro");
        System.out.println("do test3");
        shopFuture.test3("iPhone11");

        System.out.println("do testParallel");
        shopFuture.testParallel(products);
        System.out.println("do testParallel limit 5");
        shopFuture.testParallel(products.stream().limit(5).collect(toList()));
        System.out.println("do testParallel2");
        shopFuture.testParallel2(products);
        System.out.println("do testParallel3");
        shopFuture.testParallel3(products);
        System.out.println("do test getInfo");
        shopFuture.testGetInfo(products.stream().limit(2).collect(toList()));
        System.out.println("do test testGetInfoWithFuture");
        shopFuture.testGetInfoWithFuture(products);
        System.out.println("do test testGetInfoWithFuture limit 5");
        shopFuture.testGetInfoWithFuture(products.stream().limit(5).collect(toList()));
        System.out.println("do test testGetInfoRealTimeWithFuture");
        shopFuture.testGetInfoRealTimeWithFuture(products);
        System.out.println("end test");

    }

    public void test1(String product) {

        // 调用前
        System.out.println("begin");
        Future<Double> iPhone13ProMax = getPriceAsync(product);
        // 调用后，立即执行
        System.out.println("doSomething");
        try {
            System.out.println(iPhone13ProMax.get());
        } catch (InterruptedException e) {
            System.out.println("当前线程在等待过程中被中断");
            System.err.println(e);
        } catch (ExecutionException e) {
            System.out.println("线程池调用异常");
            System.err.println(e);
        }
        // 等待 future 执行后才会执行
        System.out.println("end");
    }

    /**
     * 异步执行获取价格
     *
     * @param product 商品
     * @return 价格
     */
    public Future<Double> getPriceAsync(String product) {
        // 创建CompletableFuture对象
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();

        new Thread(() -> {
            // 在另一个线程中执行计算
            double price = ShopService.getPrice(product);
            // 需要长时间计算的任务结束并得出结果时，设置future的返回值
            futurePrice.complete(price);
        }).start();
        return futurePrice;
    }


    public void test2(String product) {

        // 调用前
        System.out.println("begin");
        Future<Double> iPhone13ProMax = getPriceAsync2(product);
        // 调用后，立即执行
        System.out.println("doSomething");
        try {
            // 如果发生了错误，那么get会永久的被阻塞
            // 重载的get方法，让它超过一个时间后就强制返回
            Double result = iPhone13ProMax.get(1, TimeUnit.SECONDS);
            System.out.println(result);
        } catch (InterruptedException e) {
            System.out.println("当前线程在等待过程中被中断");
            System.err.println(e);
        } catch (ExecutionException e) {
            System.out.println("线程池调用异常");
            System.err.println(e);
        } catch (TimeoutException e) {
            // 超时会引发 TimeoutException
            // 结合 completeExceptionally 方法，获取异常原因
            System.out.println("future对象完成之前已过期");
            System.err.println(e);
        }
        // 等待 future 执行后才会执行
        System.out.println("end");
    }

    public Future<Double> getPriceAsync2(String product) {
        // 创建CompletableFuture对象
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();

        new Thread(() -> {
            double price = ShopService.getPrice(product);
            futurePrice.complete(price);
        }).start();
        return futurePrice;
    }

    public void test3(String product) {

        // 调用前
        System.out.println("begin");

        Future<Double> iPhone13ProMax = CompletableFuture.supplyAsync(() -> ShopService.getPrice(product));
        // 调用后，立即执行
        System.out.println("doSomething");
        try {
            System.out.println(iPhone13ProMax.get());
        } catch (InterruptedException e) {
            System.out.println("当前线程在等待过程中被中断");
            System.err.println(e);
        } catch (ExecutionException e) {
            System.out.println("线程池调用异常");
            System.err.println(e);
        }
        // 等待 future 执行后才会执行
        System.out.println("end");
    }

    public void testParallel(List<Product> products) {

        System.out.println("testParallel begin");

        long start = System.nanoTime();
        List<String> str = products.parallelStream().map(product -> {
            return String.format("%s price: %.2f", product.getName(), ShopService.getPrice(product.getName()));
        }).collect(toList());
        System.out.println(str);
        long end = System.nanoTime();
        System.out.println((end - start) / 1000000);
        System.out.println("testParallel end");
    }

    public void testParallel2(List<Product> products) {

        System.out.println("testParallel2 begin");

        long start = System.nanoTime();

        List<CompletableFuture<String>> str = products.stream()
                .map(product ->
                        CompletableFuture.supplyAsync(() ->
                                String.format("%s price: %.2f", product.getName(), ShopService.getPrice(product.getName())))
                )
                .collect(toList());

        // CompletableFuture类中的join和Future接口中的get方法有相同的含义，并且声明在Future接口中，
        // 唯一的不同是join不会抛出任何检测到的异常
        List<String> str3 = str.stream().map(CompletableFuture::join).collect(toList());
        System.out.println(str3);
        long end = System.nanoTime();
        System.out.println((end - start) / 1000000);
        System.out.println("testParallel2 end");
    }

    public void testParallel3(List<Product> products) {

        System.out.println("testParallel3 begin");
        final Executor executor = getExecutor(products);
        long start = System.nanoTime();

        List<CompletableFuture<String>> str = products.stream()
                .map(product ->
                        CompletableFuture.supplyAsync(() ->
                                String.format("%s price: %.2f", product.getName(), ShopService.getPrice(product.getName())
                                ), executor))
                .collect(toList());

        // CompletableFuture类中的join和Future接口中的get方法有相同的含义，并且声明在Future接口中，
        // 唯一的不同是join不会抛出任何检测到的异常
        List<String> str3 = str.stream().map(CompletableFuture::join).collect(toList());
        System.out.println(str3);
        long end = System.nanoTime();
        System.out.println((end - start) / 1000000);
        System.out.println("testParallel3 end");
    }

    private Executor getExecutor(List<Product> products) {
        // 创建一个线程池，线程池的数目为100何商店数目二者中较小的一个值
        final Executor executor = Executors.newFixedThreadPool(Math.min(products.size(), 100),
                r -> {
                    Thread t = new Thread(r);
                    // 使用守护线程，这种方式不会阻止程序的关停
                    t.setDaemon(true);
                    return t;
                });
        return executor;
    }

    public void testGetInfo(List<Product> products) {
        long start = System.nanoTime();
        List<String> str = products.stream()
                // 获取 one:120.10:GOLDD 格式字符串
                .map(s -> ShopService.getProductInfo(s))
                // 转换为 Quote 对象
                .map(Quote::parse)
                // 返回 Quote的shop中的折扣价格
                .map(s -> ShopService.applyDiscount(s))
                .collect(toList());
        long end = System.nanoTime();
        System.out.println((end - start) / 1000000);
        System.out.println(str);
    }

    public void testGetInfoWithFuture(List<Product> products) {
        Executor executor = getExecutor(products);
        long start = System.nanoTime();
        List<CompletableFuture<String>> priceFutures = products.stream()
                // 异步获取每个shop中的价格
                .map(product -> CompletableFuture.supplyAsync(() -> ShopService.getProductInfo(product), executor))
                // Quote对象存在时，对其返回值进行转换
                // 将两个异步操作进行流水线，当第一个操作完成时，将其结果作为参数传递给第二个操作
                .map(future -> future.thenApply((Quote::parse)))
                // 使用另一个异步任务构造期望的future，申请折扣
                .map(future -> future.thenCompose(quote ->
                        CompletableFuture.supplyAsync(() -> ShopService.applyDiscount(quote), executor)))
                .collect(toList());

        // 等待流中的所有Future执行完毕，提取各自的返回值
        List<String> str = priceFutures.stream().map(CompletableFuture::join).collect(toList());

        long end = System.nanoTime();
        System.out.println((end - start) / 1000000);
        System.out.println(str);
    }

    public void testGetInfoRealTimeWithFuture(List<Product> products) {
        System.out.println("begin real time get info ---------------------------");
        Executor executor = getExecutor(products);
        long start = System.nanoTime();
        CompletableFuture[] futures = products.stream()
                // 异步获取每个shop中的价格
                .map(product -> CompletableFuture.supplyAsync(() -> ShopService.getProductInfo(product), executor))
                // Quote对象存在时，对其返回值进行转换
                // 将两个异步操作进行流水线，当第一个操作完成时，将其结果作为参数传递给第二个操作
                .map(future -> future.thenApply((Quote::parse)))
                // 使用另一个异步任务构造期望的future，申请折扣
                .map(future -> future.thenCompose(quote ->
                        CompletableFuture.supplyAsync(() -> ShopService.applyDiscount(quote), executor)))
                // 在每个 CompletableFuture 上注册一个操作，该操作会在 CompletableFuture 完成后使用它的返回值。
                // 使用 thenAccept 将结果输出，它的参数就是 CompletableFuture 的返回值。
                .map(f -> f.thenAccept(System.out::println))
                // 你可以把构成的Stream的所有CompletableFuture<void>对象放到一个数组中，等待所有的任务执行完成
                .toArray(size -> new CompletableFuture[size]);

        // allOf 方法接受一个 CompletableFuture 构成的数组，数组中所有的 CompletableFuture 对象执行完成后，
        // 它返回一个 CompletableFuture<Void> 对象。所以你需要哦等待最初 Stream 中的所有 CompletableFuture 对象执行完毕，
        // 对 allOf 方法返回的 CompletableFuture 执行 join 操作
        CompletableFuture.allOf(futures).join();

        long end = System.nanoTime();
        System.out.println((end - start) / 1000000);
        System.out.println("end real time get info ---------------------------");
    }

}
