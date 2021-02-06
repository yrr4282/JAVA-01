package conc;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Homework7countDownLatch {
    public static ExecutorService executorService = Executors.newFixedThreadPool(1);

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        int result = countDownLatchMethod();

        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }
    private static int countDownLatchMethod() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            List<Integer> result = new CopyOnWriteArrayList<>();
            executorService.execute(() -> {
                result.add(sum());
                countDownLatch.countDown();
            });
            countDownLatch.await();
            return result.get(0);
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
