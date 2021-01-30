package io.gateway.inbound;

import io.gateway.filter.HeaderHttpRequestFilter;
import io.gateway.filter.HttpRequestFilter;
import io.gateway.outbound.HttpOutboundServerHandlerOne;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;

import java.util.List;

public class HttpInboundServerHandler extends ChannelInboundHandlerAdapter {

    private HttpRequestFilter inputFilter;

    private HttpOutboundServerHandlerOne handlerOne;

    public HttpInboundServerHandler(List<String> backServerUrls) {
        inputFilter = new HeaderHttpRequestFilter();
        handlerOne = new HttpOutboundServerHandlerOne(backServerUrls);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("开始读取channel内的数据流; 接受到:" + msg);
        try {
            FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
            //对输入进行过滤
            inputFilter.filter(fullHttpRequest, ctx);
            handlerOne.handleTaskOne(fullHttpRequest, ctx);

        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server channelReadComplete..");
        //写一个空的buf，并刷新写出区域。完成后关闭sock channel连接。
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 关闭发生异常的连接
        System.out.println("server occur exception:" + cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }


}
