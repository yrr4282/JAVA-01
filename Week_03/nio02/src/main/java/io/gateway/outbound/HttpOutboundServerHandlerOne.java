package io.gateway.outbound;

import io.gateway.filter.HeaderHttpResponseFilter;
import io.gateway.filter.HttpResponseFilter;
import io.gateway.router.HttpEndpointRouter;
import io.gateway.router.RandomHttpEndpointRouter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static org.apache.http.HttpHeaders.CONNECTION;


public class HttpOutboundServerHandlerOne {
    private CloseableHttpClient httpclient = HttpClients.createDefault();
    private List<String> backendUrls;
    /**
     * 路由转发
     */
    HttpEndpointRouter router;

    public HttpOutboundServerHandlerOne(List<String> backServerUrls) {
        router = RandomHttpEndpointRouter.getRoute();
        this.backendUrls = backServerUrls;
    }

    /**
     * @param fullHttpRequest 输入的请求
     * @param ctx             上下文
     * @author hongzhengwei
     * @create 2021/1/24 上午11:53
     * @desc 对输入的请求进行转发和处理增强
     */
    public void handleTaskOne(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx) throws IOException {
        FullHttpResponse response = null;
        //作业1：将固定回复替换为，http发起的后端请求
        //String value = "hello hongzw";
        //String value = getAsString("http://localhost:8088/api/hello");
        //作业2：进行路由器

        String url = router.roundRobin(this.backendUrls);
        String value = getAsString(url);
        try {
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK, Unpooled.wrappedBuffer(value.getBytes(StandardCharsets.UTF_8)));
            response.headers().set("Content-Type", "application/json");
            response.headers().set("Content-Length", response.content().readableBytes());
            response.headers().set("requestFilter", fullHttpRequest.headers().get("requestFilter"));
            HttpResponseFilter outFilter = new HeaderHttpResponseFilter();
            outFilter.filter(response);
        } finally {
            if (fullHttpRequest != null) {
                if (!HttpUtil.isKeepAlive(fullHttpRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    if(response!=null){
                        response.headers().set(CONNECTION, KEEP_ALIVE);
                    }
                    ctx.write(response);
                }
            }
        }
    }


    // GET 调用
    public String getAsString(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response1 = null;
        try {
            response1 = httpclient.execute(httpGet);
            System.out.println(response1.getStatusLine());
            HttpEntity entity1 = response1.getEntity();
            return EntityUtils.toString(entity1, "UTF-8");
        } finally {
            if (null != response1) {
                response1.close();
            }
        }
    }




}