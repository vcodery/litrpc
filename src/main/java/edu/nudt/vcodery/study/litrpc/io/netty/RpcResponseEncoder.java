package edu.nudt.vcodery.study.litrpc.io.netty;

import edu.nudt.vcodery.study.litrpc.model.RpcResponse;
import edu.nudt.vcodery.study.litrpc.serializer.Serializer;
import edu.nudt.vcodery.study.litrpc.serializer.SerializerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * response type: <br/>
 * ++------------------++-------------------++ <br/>
 * || RpcResponse size || RpcResponse Bytes || <br/>
 * ++------------------++-------------------++ <br/>
 */
public class RpcResponseEncoder extends MessageToByteEncoder<RpcResponse> {

	private Serializer serializer = SerializerFactory.getInstace();

	@Override
	protected void encode(ChannelHandlerContext arg0, RpcResponse arg1, ByteBuf arg2) throws Exception {
		// TODO Auto-generated method stub
		byte[] b = serializer.buildObject(arg1);
		arg2.writeInt(b.length);
		arg2.writeBytes(b);
	}

}
