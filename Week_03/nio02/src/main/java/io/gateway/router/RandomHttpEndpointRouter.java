package io.gateway.router;

import java.util.List;
import java.util.Random;

public class RandomHttpEndpointRouter implements HttpEndpointRouter {
    private static RandomHttpEndpointRouter singleRoute = null;

    public static RandomHttpEndpointRouter getRoute() {
        if (singleRoute == null) {
            singleRoute = new RandomHttpEndpointRouter();
        }
        return singleRoute;

    }

    private RandomHttpEndpointRouter() {

    }

    /**
     * 随机路由
     *
     * @param
     * @return
     */
    @Override
    public String randomRoute(List<String> urls) {
        int size = urls.size();
        Random random = new Random(System.currentTimeMillis());
        return urls.get(random.nextInt(size));
    }

    /**
     * 轮训的下标
     */
    private int randomRobbinIndex = 0;

    /**
     * @param endpoints
     * @author hongzhengwei
     * @create 2021/1/24 下午5:16
     * @desc 轮训算法
     */
    @Override
    public String roundRobin(List<String> endpoints) {
        int index = (randomRobbinIndex + 1) % endpoints.size();
        randomRobbinIndex = randomRobbinIndex+1;
        System.out.println("本次轮训的下标是：" + index+"游标是："+randomRobbinIndex);
        return endpoints.get(index);
    }
}
