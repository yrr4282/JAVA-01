package conc;

import java.util.List;
import java.util.concurrent.*;

public class Homework1jion {
    private static ExecutorService executorService = Executors.newFixedThreadPool(1);

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        int result = newThread();

        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    private static int newThread() {
        List<Integer> result = new CopyOnWriteArrayList<>();
        try {
            Thread thread = new Thread(() -> result.add(sum()));
            thread.start();
            thread.join();
            return result.get(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
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

