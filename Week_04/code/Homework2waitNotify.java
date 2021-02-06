package conc;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Homework2waitNotify {

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();

        int result = waitAndNotify();

        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    private static int waitAndNotify() throws InterruptedException {
        List<Integer> result = new CopyOnWriteArrayList<>();
        Object obj = new Object();
        Thread thread = new Thread(() -> {
            synchronized (obj) {
                result.add(sum());
                obj.notifyAll();
            }
        });
        thread.start();
        synchronized (obj) {
            obj.wait();
           return result.get(0);
        }
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
