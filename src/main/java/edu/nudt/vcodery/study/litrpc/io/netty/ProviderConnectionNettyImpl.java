package edu.nudt.vcodery.study.litrpc.io.netty;

import edu.nudt.vcodery.study.litrpc.io.ProviderConnection;
import edu.nudt.vcodery.study.litrpc.model.RpcResponse;
import io.netty.channel.ChannelHandlerContext;

public class ProviderConnectionNettyImpl extends ProviderConnection{
	
	private ChannelHandlerContext chContext;
	
	public ProviderConnectionNettyImpl(ChannelHandlerContext chContext) {
		super();
		this.chContext = chContext;
	}

	@Override
	public void doResponse(RpcResponse response) {
		// TODO Auto-generated method stub
		chContext.writeAndFlush(response);
	}

}
