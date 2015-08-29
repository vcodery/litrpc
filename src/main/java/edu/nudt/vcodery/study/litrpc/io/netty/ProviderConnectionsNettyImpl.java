package edu.nudt.vcodery.study.litrpc.io.netty;

import java.io.IOException;

import edu.nudt.vcodery.study.litrpc.conf.Configuration;
import edu.nudt.vcodery.study.litrpc.io.ProviderConnections;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class ProviderConnectionsNettyImpl extends ProviderConnections {

	public static final int READ_TIMEOUT = 4 ;

	public ProviderConnectionsNettyImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	ChannelFuture channelFuture = null;

	EventLoopGroup bossGroup = new NioEventLoopGroup();
	EventLoopGroup workerGroup = new NioEventLoopGroup();

	@Override
	public void startUpOn(String host, int port) {
		// TODO Auto-generated method stub

		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup)// boss and worker
				.channel(NioServerSocketChannel.class)// 
				.option(ChannelOption.SO_BACKLOG, 100)// 
				.handler(new LoggingHandler(LogLevel.INFO))// log handler
				.childHandler(new ChannelInitializer<SocketChannel>()// child handler
		{
					@Override
					public void initChannel(SocketChannel ch) throws IOException {
						ch.pipeline().addLast("RPC-Request-Decder", new RpcRequestDecoder(1024 * 1024, 4, 4));
						ch.pipeline().addLast("RPC-Response-Encoder", new RpcResponseEncoder());
						ch.pipeline().addLast("RPC-ReadTimeout-Handler", new ReadTimeoutHandler(Configuration.CONNECTION_ACTIVE_TIMEOUT));
						ch.pipeline().addLast("RPC-Provider-Handler", new RpcProviderHandler(provider));
					}
				});

		try {
			channelFuture = b.bind(host, port).sync();// block until ready
			System.out.println("Netty Server started...");

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	@Override
	public void shutDown() throws IOException {
		// TODO Auto-generated method stub
		try {
			channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

}
