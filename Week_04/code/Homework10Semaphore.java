package conc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Homework10Semaphore {

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();

        int result = Semaphore();

        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }
    private static int Semaphore() throws InterruptedException {
            AtomicInteger result = new AtomicInteger();
            Semaphore semaphore = new Semaphore(1);
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    result.set(sum());
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            TimeUnit.MILLISECONDS.sleep(100);
            return result.get();
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
