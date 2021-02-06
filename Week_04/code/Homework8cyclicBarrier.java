package conc;

import java.util.concurrent.*;

public class Homework8cyclicBarrier {
    public static ExecutorService executorService = Executors.newFixedThreadPool(1);

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        int result = cyclicBarrierMethod();

        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }
    private static int cyclicBarrierMethod() {
        try {
            CyclicBarrier cyclicBarrier = new CyclicBarrier(1);
            Future<Integer> future = executorService.submit(() -> {
                try {
                    int sum = sum();
                    cyclicBarrier.await();
                    return sum;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return -1;
            });
            return future.get();
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
