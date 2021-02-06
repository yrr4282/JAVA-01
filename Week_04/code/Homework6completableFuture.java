package conc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Homework6completableFuture {
    private static ExecutorService executorService = Executors.newFixedThreadPool(1);
    private static ExecutorService executorService2 = Executors.newFixedThreadPool(1);

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        int result = completableFutureMethod1();

        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        int result2 = completableFutureMethod2();

        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    private static int completableFutureMethod1() {
        List<Integer> result = new ArrayList<>();
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            result.add(sum());
        }, executorService);
        try {
            future.get();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        return result.get(0);
    }
    private static int completableFutureMethod2() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> sum(), executorService2);
        try {
            Integer result = future.get();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        return -1;
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}
