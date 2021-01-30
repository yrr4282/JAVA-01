package io.gateway.inbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.util.List;

public class HttpInitializer extends ChannelInitializer<SocketChannel> {

	private List<String> backServerUrls;

	public HttpInitializer(List<String> backServerUrls) {
		this.backServerUrls = backServerUrls;
	}

	@Override
	public void initChannel(SocketChannel ch) {
		ChannelPipeline p = ch.pipeline();
		p.addLast(new HttpServerCodec());
		p.addLast(new HttpObjectAggregator(1024 * 1024));
		//处理过程
		p.addLast(new HttpInboundServerHandler(this.backServerUrls));
	}
}