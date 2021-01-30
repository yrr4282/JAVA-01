package io.gateway;


import io.gateway.inbound.HttpServer;

import java.util.Arrays;

public class NettyServerApplication {

    /**
     * 网关名字
     */
    public final static String GATEWAY_NAME = "NettyGateway";
    /**
     * 网关版本
     */
    public final static String GATEWAY_VERSION = "3.0";
    /**
     * 网关入口
     */
    public final static int PROXY_PORT = 2100;

    public static void main(String[] args) {
        //初始化后端服务请求的数据
        String proxyServers = System.getProperty("proxyServers", "http://localhost:8088/api/hello,http://localhost:8803");
        HttpServer server = new HttpServer(PROXY_PORT, Arrays.asList(proxyServers.split(",")));
        try {
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
