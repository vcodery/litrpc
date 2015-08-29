package edu.nudt.vcodery.study.litrpc.io.netty;

import java.io.IOException;

import edu.nudt.vcodery.study.litrpc.conf.Configuration;
import edu.nudt.vcodery.study.litrpc.io.ConsumerConnection;
import edu.nudt.vcodery.study.litrpc.model.RpcResponseContainer;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class ConsumerConnectionNettyImpl extends ConsumerConnection {

	private EventLoopGroup group = new NioEventLoopGroup();

	private ChannelFuture channelFuture = null;
	private Channel channel;

	@Override
	public void connectTo(String host, int port) throws IOException {
		// TODO Auto-generated method stub
		Bootstrap b = new Bootstrap();
		b.group(group) // add work group
				.channel(NioSocketChannel.class) // add channel class
				.option(ChannelOption.TCP_NODELAY, true) // set no delay
				.handler(new ChannelInitializer<SocketChannel>() // handler
		{
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast("RPC-Response-Decoder", new RpcResponseDecoder(1024 * 1024, 4, 4));
						ch.pipeline().addLast("RPC-ReadTimeout-Handler", new ReadTimeoutHandler(Configuration.CONNECTION_ACTIVE_TIMEOUT));
						ch.pipeline().addLast("RPC-Consumer-Handler", new RpcConsumerHandler(waitingResponse));
					}
				});

		try {
			channelFuture = b.connect(host, port).sync();
			channel = channelFuture.channel();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public RpcResponseContainer send(ByteBuf buffer) {
		// TODO Auto-generated method stub
		int ticket = ticketCreater.incrementAndGet();

		buffer.writeInt(ticket);
		channel.writeAndFlush(buffer);

		RpcResponseContainer rc = new RpcResponseContainer();
		waitingResponse.put(ticket, rc);
		return rc;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		if (null != channelFuture) {
			try {
				channelFuture.channel().closeFuture().sync();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				group.shutdownGracefully();
			}
		}
	}

}
