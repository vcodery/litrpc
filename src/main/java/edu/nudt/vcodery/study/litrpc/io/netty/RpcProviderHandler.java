package edu.nudt.vcodery.study.litrpc.io.netty;

import edu.nudt.vcodery.study.litrpc.RpcProvider;
import edu.nudt.vcodery.study.litrpc.model.RpcRequest;
import edu.nudt.vcodery.study.litrpc.model.RpcResponse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RpcProviderHandler extends SimpleChannelInboundHandler<RpcRequest> {

	RpcProvider provider;

	public RpcProviderHandler(RpcProvider provider) {
		super();
		this.provider = provider;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, RpcRequest arg1) throws Exception {
		// TODO Auto-generated method stub

		RpcResponse response = provider.handle(arg1);
//		if (!arg1.isTimeOut())
			arg0.writeAndFlush(response);

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		// System.out.println("Connection is going to close, since long time not
		// used!");
		cause.printStackTrace();
		ctx.close();
	}

}
