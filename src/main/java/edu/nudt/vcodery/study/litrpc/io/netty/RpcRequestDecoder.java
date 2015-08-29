package edu.nudt.vcodery.study.litrpc.io.netty;

import edu.nudt.vcodery.study.litrpc.model.RpcRequestBody;
import edu.nudt.vcodery.study.litrpc.model.RpcRequest;
import edu.nudt.vcodery.study.litrpc.model.RpcRequestKey;
import edu.nudt.vcodery.study.litrpc.serializer.Serializer;
import edu.nudt.vcodery.study.litrpc.serializer.SerializerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * decode request ByteBuf to RpcRequestImpl as: <br/>
 * ++-----------------++----------------------++ <br/>
 * || RequestKey Size || RpcRequestKeyContent || <br/>
 * ++-----------------++----------------------++ <br/>
 * || RequestBodySize || RpcRequestBody Bytes || <br/>
 * ++-----------------++----------------------++ <br/>
 * || (Integer)ticket || ==================== || <br/>
 * ++-----------------++----------------------++ <br/>
 * 
 */
public class RpcRequestDecoder extends LengthFieldBasedFrameDecoder {

	private Serializer serializer = SerializerFactory.getInstace();

	public RpcRequestDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Object decode(ChannelHandlerContext arg0, ByteBuf arg1) throws Exception {
		// TODO Auto-generated method stub
		
		RpcRequestKey key = new RpcRequestKey();
		key.readFrom(arg1);

		RpcRequestBody body = new RpcRequestBody();
		byte[] bodyBytes = new byte[arg1.readInt()];
		arg1.readBytes(bodyBytes);
		serializer.parseObject(body, bodyBytes);

		RpcRequest request = new RpcRequest(key, body, arg1.readInt());
		
//		System.out.println(request);

		return request;
	}

}
