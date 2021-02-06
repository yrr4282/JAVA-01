package conc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Homework9ReentrantLock {
    private static volatile Integer value = null;
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();

        int result = ReentrantLock();

        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    private static int  ReentrantLock() throws InterruptedException {
        lock.lock();
        try {
            while (value == null) {
                new Thread(() -> { sum(); }).start();
                condition.await();
            }
        } finally {
            lock.unlock();
        }
        return value;
    }

    private static void sum() {
        lock.lock();
        try {
            value = fibo(36);
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}
