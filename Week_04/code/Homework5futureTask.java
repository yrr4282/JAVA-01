package conc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Homework5futureTask {
    private static ExecutorService executorService = Executors.newFixedThreadPool(1);

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        int result = futureTaskMethod1();

        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        long start2 = System.currentTimeMillis();

        int result2 = futureTaskMethod2();

        System.out.println("异步计算结果为：" + result2);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start2) + " ms");
    }


    private static int futureTaskMethod1() {
        try {
            FutureTask futureTask = new FutureTask(() -> sum());
            new Thread(futureTask).start();
            return (Integer) futureTask.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private static int futureTaskMethod2() {
        try {
            FutureTask futureTask = new FutureTask(() -> sum());
            executorService.execute(futureTask);
            return (Integer) futureTask.get();
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
