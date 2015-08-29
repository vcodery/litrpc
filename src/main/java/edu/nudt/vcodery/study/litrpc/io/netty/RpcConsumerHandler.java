package edu.nudt.vcodery.study.litrpc.io.netty;

import java.util.HashMap;
import java.util.Map;

import edu.nudt.vcodery.study.litrpc.model.RpcResponseContainer;
import edu.nudt.vcodery.study.litrpc.model.RpcResponse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RpcConsumerHandler extends SimpleChannelInboundHandler<RpcResponse>{
	
	protected Map<Integer, RpcResponseContainer> waitingResponse = new HashMap<Integer, RpcResponseContainer>();

	public RpcConsumerHandler(Map<Integer, RpcResponseContainer> waitingResponse) {
		super();
		this.waitingResponse = waitingResponse;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, RpcResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		RpcResponseContainer container = waitingResponse.get(arg1.getTicket());
		if ( null != container){
			container.put(arg1);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
//		System.out.println("Connection is going to close, since long time not used!");
		cause.printStackTrace();
		ctx.close();
	}
	
}
