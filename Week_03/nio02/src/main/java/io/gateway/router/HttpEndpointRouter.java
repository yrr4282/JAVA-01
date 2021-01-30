package io.gateway.router;

import java.util.List;

public interface HttpEndpointRouter {

    /**
     * 随机路由
     *
     * @param endpoints
     * @return
     */
    String randomRoute(List<String> endpoints);


    /**
     * @author hongzhengwei
     * @create 2021/1/24 下午5:16
     * @desc 轮训算法
     **/
    String roundRobin(List<String> endpoints);
    
}
