package conc;

import java.util.concurrent.TimeUnit;


public class Homework12volatile {
    public static volatile int result;

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();

        int result = Semaphore();

        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    private static int Semaphore() throws InterruptedException {
        new Thread(() -> result = sum()).start();
        TimeUnit.MILLISECONDS.sleep(100);
        return result;
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