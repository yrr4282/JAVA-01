package conc;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class Homework11LockSupport {

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();

        int result = LockSupport();

        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }
    private static int LockSupport() throws InterruptedException {
        AtomicInteger result = new AtomicInteger();
        Thread main = Thread.currentThread();
        Thread thread = new Thread(() -> {
            result.set(sum());
            LockSupport.unpark(main);
        });
        thread.start();
        LockSupport.park();
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
