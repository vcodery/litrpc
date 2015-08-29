package edu.nudt.vcodery.study.litrpc.io.netty;

import edu.nudt.vcodery.study.litrpc.model.RpcResponse;
import edu.nudt.vcodery.study.litrpc.serializer.Serializer;
import edu.nudt.vcodery.study.litrpc.serializer.SerializerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * response type: <br/>
 * ++------------------++-------------------++ <br/>
 * || RpcResponse size || RpcResponse Bytes || <br/>
 * ++------------------++-------------------++ <br/>
 */
public class RpcResponseDecoder extends LengthFieldBasedFrameDecoder {

	private Serializer serializer = SerializerFactory.getInstace();

	public RpcResponseDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Object decode(ChannelHandlerContext arg0, ByteBuf arg1) throws Exception {
		// TODO Auto-generated method stub
		
		byte[] b = new byte[arg1.readInt()];
		arg1.readBytes(b);
		
		RpcResponse response = new RpcResponse();
		serializer.parseObject(response, b);
		
//		System.out.println(response);
		return response;
	}

}
